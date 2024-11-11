package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.entity.Cart;
import com.example.indoor.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    CartService cartService;
//    カート画面表示
    @GetMapping("/cart")
    public ModelAndView cartForm() {
        ModelAndView mav = new ModelAndView();
        AccountForm loginAccount = new AccountForm();
        List<CartForm> cart = cartService.findCart(loginAccount);
        return mav;
    }
}
