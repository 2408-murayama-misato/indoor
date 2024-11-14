package com.example.indoor.controller;

import com.example.indoor.controller.form.*;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.Purchase;
import com.example.indoor.service.AccountService;
import com.example.indoor.service.CartService;
import com.example.indoor.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    CartService cartService;

    @Autowired
    PurchaseService purchaseService;

    /*
     * 購入画面表示
     */
    @GetMapping("/purchase")
    public ModelAndView purchase(@AuthenticationPrincipal Account loginAccount,
                                 @ModelAttribute("searchForm") SearchForm searchForm
                                 ) {
        ModelAndView mav = new ModelAndView();
        //カート情報取得
        List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
        //ログインアカウント情報取得
        AccountForm accountForm = new AccountForm();
        accountForm = accountService.findById(loginAccount.getId());

        mav.addObject("cartForms", cartForms);
        mav.addObject("name", accountForm.getName());
        mav.addObject("address", accountForm.getAddress());
        mav.addObject("credit", accountForm.getCredit());
        mav.addObject("searchForm", searchForm);
        mav.addObject("formPurchase", new PurchaseForm());
        mav.setViewName("/purchase");
        return mav;
    }

    /*
     * 購入処理
     */
    @PostMapping("/purchaseProduct")
    public ModelAndView purchaseProduct(@ModelAttribute("formPurchase") PurchaseForm purchaseForm,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "address", required = false) String address,
                                        @RequestParam(value = "credit", required = false) String credit,
                                        @AuthenticationPrincipal Account loginAccount,
                                        RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();

        List<String> purchaseErrorMessages = new ArrayList<>();
        //氏名のバリデーション
        if (name.isBlank()) {
            purchaseErrorMessages.add("氏名を入力してください");
        } else if (name.length() > 10) {
            purchaseErrorMessages.add("氏名は10文字以下で入力してください");
        }

        //住所のバリデーション
        if (address.isBlank()) {
            purchaseErrorMessages.add("住所を入力してください");
        }

        //クレジットカード番号のバリデーション
        if (credit.isBlank()) {
            purchaseErrorMessages.add("クレジットカード番号を入力してください");
        } else if (!credit.matches("^[0-9]+$")) {
            purchaseErrorMessages.add("クレジットカード番号は数字のみで入力してください");
        }

        if (!purchaseErrorMessages.isEmpty()) {
            redirectAttributes.addFlashAttribute("purchaseErrorMessages", purchaseErrorMessages);
            return new ModelAndView("redirect:/purchase");
        }

        //カート情報取得
        List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
        //購入済みマスタに登録
        purchaseService.savePurchases(cartForms);
        //購入した商品をカート内から削除
        cartService.deleteCart(loginAccount.getId());
        //決済完了画面に遷移
        mav.setViewName("redirect:/payment");
        return mav;
    }

    /*
     * 決済完了画面表示
     */
    @GetMapping("/payment")
    public void payment(){}

    /*
     * 売上集計画面表示(絞り込み含む)
     */
    @GetMapping("/sale")
    public ModelAndView sale(@AuthenticationPrincipal Account loginAccount,
                             @RequestParam(value = "start", required = false) String start,
                             @RequestParam(value = "end", required = false) String end,
                             @ModelAttribute("searchForm") SearchForm searchForm
    ) {
        ModelAndView mav = new ModelAndView();
        List<PurchaseForm> purchaseForms = purchaseService.findPurchases(loginAccount.getId(), start, end);
        mav.addObject("start", start);
        mav.addObject("end", end);
        mav.addObject("purchaseForms", purchaseForms);
        //合計売上計算
        int sum = 0;
        for (int i=0; i < purchaseForms.size(); i++) {
            sum += purchaseForms.get(i).getPrice();
        }
        mav.addObject("sum", sum);
        mav.addObject("searchForm", searchForm);
        mav.setViewName("/sale");
        return mav;
    }

}