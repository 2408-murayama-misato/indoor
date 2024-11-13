package com.example.indoor.controller;

import com.example.indoor.controller.form.*;
import com.example.indoor.entity.Cart;
import com.example.indoor.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class ProductController {
    


    @Autowired
    ProductService productService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/productDetail")
    public ModelAndView productDetail(@ModelAttribute("id") String id) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));

        mav.addObject("product", product);

        mav.setViewName("/productDetail");
        return mav;
    }

    @GetMapping("/reviewNew-{id}")
    public ModelAndView reviewNew(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        mav.addObject("product", product);

        ReviewForm reviewForm = new ReviewForm();
        mav.addObject("reviewForm", reviewForm);

        mav.setViewName("/reviewNew");
        return mav;
    }

    @PutMapping("/addReview")
    public ModelAndView addReview(@AuthenticationPrincipal Account account,
                                  @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        ModelAndView mav = new ModelAndView();

        reviewForm.setAccountId(account.getId());
        reviewService.insertReview(reviewForm);

        mav.addObject("id", reviewForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

//    商品検索
    @GetMapping("/search")
    public ModelAndView searchProduct(@Validated @ModelAttribute("searchForm") SearchForm searchForm,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        String errorMessage =  null;
        if (isBlank(searchForm.getKeyWord()) && isBlank(searchForm.getCategory())) {
            mav.setViewName("redirect:/top");
            return mav;
        }
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage = error.getDefaultMessage();
            }
        }
        if (errorMessage != null) {
            redirectAttributes.addFlashAttribute("errorMessage",errorMessage);
            redirectAttributes.addFlashAttribute("searchForm", searchForm);
            mav.setViewName("redirect:/top");
        } else {
        List<ProductForm> products = productService.findAllProduct(searchForm);
        mav.addObject("productForm", products);
        mav.addObject("searchForm", searchForm);
        mav.setViewName("/productList");
        }
        return mav;

    }
}
