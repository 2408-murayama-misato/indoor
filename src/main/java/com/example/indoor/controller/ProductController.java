package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
