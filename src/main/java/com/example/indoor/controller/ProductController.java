package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    /*
     * 商品問い合わせ登録処理
     */
    @PostMapping("/productDetail/{id}")
    public ModelAndView addProductContact(@ModelAttribute("productNotice") @Validated ProductsNoticeForm productsNoticeForm,
                                          @PathVariable int productId, @AuthenticationPrincipal Account account, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        int accountId = account.getId();
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
        productService.createProductContact(productsNoticeForm, productId, account);
        mav.setViewName("redirect:/productDetail");
        return mav;
    }
}
