package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.AccountService;
import com.example.indoor.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PurchaseController {

    @Autowired
    AccountService accountService;

    @Autowired
    CartService cartService;

    @GetMapping("/purchase")
    public ModelAndView purchase(@AuthenticationPrincipal Account loginAccount) {
        ModelAndView mav = new ModelAndView();
        //カート情報取得
        List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
        //ログインアカウント情報取得
        AccountForm accountForm = new AccountForm();
        accountForm = accountService.findById(loginAccount.getId());

        mav.addObject("cartForms", cartForms);
        mav.addObject("accountForm", accountForm);
        mav.setViewName("/purchase");
        return mav;
    }
}
