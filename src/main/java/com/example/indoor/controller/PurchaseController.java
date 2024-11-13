package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.PurchaseForm;
import com.example.indoor.entity.Account;
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
    public ModelAndView purchase(@AuthenticationPrincipal Account loginAccount) {
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


        for (PurchaseForm.Purchase purchase : purchaseForm.getPurchases()) {
            purchaseService.savePurchase(purchase);
        }

//        purchaseService.savePurchase(purchaseForm);
        //決済完了画面に遷移
        mav.setViewName("redirect:/payment");
        return mav;
    }

    /*
     * 決済完了画面表示
     */
    @GetMapping("/payment")
    public void payment(){}


}