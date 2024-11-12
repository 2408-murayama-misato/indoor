package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.service.AccountService;
import com.example.indoor.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PurchaseController {

    @Autowired
    CartService cartService;

    @GetMapping("/purchase")
    public ModelAndView purchase() {
        ModelAndView mav = new ModelAndView();
        CartForm cartForm = new CartForm();
        //ログインユーザーのid取得
//        cartForm = cartService.findCartPurchase(5);
        mav.addObject("cartForm", cartForm);
        mav.setViewName("/purchase");
        return mav;
    }
}
