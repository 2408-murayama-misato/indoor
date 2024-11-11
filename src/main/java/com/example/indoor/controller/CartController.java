package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CartController {
   @Autowired
    CartService cartService;

   @GetMapping("/cart")
    public ModelAndView findCart() {
       ModelAndView mav = new ModelAndView();
       AccountForm loginAccount = new AccountForm();
       loginAccount.setId(1);
       List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
       mav.setViewName("/cart");
       mav.addObject("cartForms", cartForms);
       return mav;
   }

}