package com.example.indoor.controller;


import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ProductsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;

import com.example.indoor.entity.Cart;
import com.example.indoor.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductsNoticeService productsNoticeService;

    @GetMapping("/productDetail")
    public ModelAndView productDetail(@ModelAttribute("id") String id) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        List<ProductsNoticeForm> productContacts = productsNoticeService.findProductContacts(Integer.parseInt(id));
        ProductsNoticeForm productsNoticeForm = new ProductsNoticeForm();
        mav.addObject("product", product);
        mav.addObject("productsNoticeForm", productsNoticeForm);
        mav.addObject("productContacts", productContacts);

        mav.setViewName("/productDetail");
        return mav;
    }

    /*
     * 商品問い合わせ登録処理
     */
    @PostMapping("/productDetail")
    public ModelAndView addProductContact(@ModelAttribute("productNotice") @Validated ProductsNoticeForm productsNoticeForm,
                                          @AuthenticationPrincipal Account account, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("productsContact", productsNoticeForm);
            mav.setViewName("/productDetail");
            return mav;
        }
        productsNoticeService.createProductContact(productsNoticeForm, account);
        mav.addObject("id", productsNoticeForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }
}
