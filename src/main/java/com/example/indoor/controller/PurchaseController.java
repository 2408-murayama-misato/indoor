package com.example.indoor.controller;

import com.example.indoor.controller.form.*;
import com.example.indoor.entity.Account;
import com.example.indoor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    CartService cartService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductsNoticeService productsNoticeService;

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
                                        RedirectAttributes redirectAttributes,
                                        @ModelAttribute("searchForm") SearchForm searchForm) {
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
            mav.addObject("purchaseErrorMessages", purchaseErrorMessages);
            mav.addObject("searchForm", searchForm);
            mav.addObject("name", name);
            mav.addObject("address", address);
            mav.addObject("credit", credit);
            List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
            mav.addObject("cartForms", cartForms);
            mav.setViewName("/purchase");
            return mav;
        }

        //カート情報取得
        List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
        //購入済みマスタに登録
        purchaseService.savePurchases(cartForms);
        //購入した商品をカート内から削除
        cartService.deletePurchasedCart(loginAccount.getId());

        for (CartForm cartForm : cartForms) {
            //在庫の計算と在庫がなくなったら販売者に自動通知する処理(引数は商品の個数, 商品のID)　※暫定単体の商品のみに適応
            productService.updateProductStock(cartForm.getNumber(), cartForm.getProductId());
            //発送準備をするように販売者に通知する処理(引数は購入者のID, 購入された商品のID)
            productsNoticeService.createProductNotice(cartForm.getAccountId(), cartForm.getProductId());
        }
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
    ) throws ParseException {
        ModelAndView mav = new ModelAndView();

        //日付のバリデーション
        if (start != null && !start.isBlank() && end != null && !end.isBlank()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date startDate = dateFormat.parse(start);
            Date endDate = dateFormat.parse(end);
            List<String> dateErrorMessages = new ArrayList<>();

            if (startDate.after(endDate)) {
                dateErrorMessages.add("開始日は終了日よりも前に設定してください");
                mav.addObject("searchForm", searchForm);
                mav.addObject("dateErrorMessages", dateErrorMessages);
                int sum = 0;
                mav.addObject("sum", sum);
                mav.addObject("start", startDate);
                mav.addObject("end", endDate);
                mav.setViewName("/sale");
                return mav;
            } else {
                start = dateFormat.format(startDate);
                end = dateFormat.format(endDate);
            }
        }

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

    /*
     * 注文履歴画面表示
     */
    @GetMapping("/orderHistory")
    public ModelAndView sale(@AuthenticationPrincipal Account loginAccount, @ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView mav = new ModelAndView();
        String start = null;
        String end = null;
        List<PurchaseForm> purchaseForms = purchaseService.findPurchases(loginAccount.getId(), start, end);
        mav.addObject("purchaseForms", purchaseForms);
        mav.addObject("searchForm", searchForm);
        mav.setViewName("/orderHistory");
        return mav;
    }
}