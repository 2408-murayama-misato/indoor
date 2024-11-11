package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/productDetail")
    public ModelAndView productDetail() {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(1);

        mav.addObject("product", product);

        mav.setViewName("/productDetail");
        return mav;
    }
}
