package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

        List<ReviewForm> reviews = reviewService.findUserReviews();
        mav.addObject("reviews", reviews);

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

    @DeleteMapping("/deleteReview/{id}-{productId}")
    public ModelAndView delete(@PathVariable int id,
                               @PathVariable int productId) {
        ModelAndView mav = new ModelAndView();
        reviewService.deleteReview(id);
        mav.addObject("id", productId);
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

    @GetMapping("/reviewEdit/{id}")
    public ModelAndView reviewEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        ReviewForm review = reviewService.findReview(Integer.parseInt(id));
        mav.addObject("reviewForm", review);
        mav.setViewName("/reviewEdit");
        return mav;
    }

    @PutMapping("/updateReview")
    public ModelAndView updateReview(@AuthenticationPrincipal Account account,
                                     @ModelAttribute("reviewForm") ReviewForm reviewForm) {
        ModelAndView mav = new ModelAndView();

        reviewForm.setAccountId(account.getId());
        reviewService.updateReview(reviewForm);

        mav.addObject("id", reviewForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }
}
