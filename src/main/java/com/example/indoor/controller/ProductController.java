package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ReviewService;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.service.ProductsNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductsNoticeService productsNoticeService;

    @Autowired
    ReviewService reviewService;

    /*
     * 5-1.商品詳細画面表示
     */
    @GetMapping("/productDetail")
    public ModelAndView productDetail(@ModelAttribute("id") String id) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        List<ReviewForm> reviews = reviewService.findUserReviews();
        List<ProductsNoticeForm> productContacts = productsNoticeService.findProductContacts(Integer.parseInt(id));
        ProductsNoticeForm productsNoticeForm = new ProductsNoticeForm();
        mav.addObject("product", product);
        mav.addObject("reviews", reviews);
        mav.addObject("productsNoticeForm", productsNoticeForm);
        mav.addObject("productContacts", productContacts);

        mav.setViewName("/productDetail");
        return mav;
    }

    /*
     * 商品問い合わせ登録処理
     */
    @PostMapping("/productDetail")
    public ModelAndView addProductContact(@ModelAttribute("productsNoticeForm") @Validated ProductsNoticeForm productsNoticeForm,
                                          BindingResult bindingResult, @AuthenticationPrincipal Account account) {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            int id = productsNoticeForm.getProductId();
            ProductForm product = productService.findProduct(id);
            mav.addObject("product", product);
            mav.addObject("errorMessages", errorMessages);
            mav.addObject("productsNoticeForm", productsNoticeForm);
            mav.addObject("id", id);
            List<ProductsNoticeForm> productContacts = productsNoticeService.findProductContacts(id);
            mav.addObject("productContacts", productContacts); //既にある商品問い合わせのやり取り
            mav.setViewName("/productDetail");
            return mav;
        }
        productsNoticeService.createProductContact(productsNoticeForm, account);
        mav.addObject("id", productsNoticeForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

    /*
     * 6-1.レビュー投稿画面表示
     */
    @GetMapping("/reviewNew-{id}")
    public ModelAndView reviewNew(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        mav.addObject("product", product);

        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setProductId(Integer.parseInt(id));
        mav.addObject("reviewForm", reviewForm);

        mav.setViewName("/reviewNew");
        return mav;
    }

    /*
     * 6-2.レビュー投稿処理
     */
    @PutMapping("/addReview")
    public ModelAndView addReview(@AuthenticationPrincipal Account account,
                                  @Validated @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                  BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();

        List<String> errorList = new ArrayList<String>();
        if (bindingResult.hasErrors()) {
            /*for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }*/
            ProductForm product = productService.findProduct(reviewForm.getProductId());
            mav.addObject("product", product);
            mav.addObject("reviewForm", reviewForm);
            mav.setViewName("/reviewNew");
            return mav;
        }

        reviewForm.setAccountId(account.getId());
        reviewService.insertReview(reviewForm);

        mav.addObject("id", reviewForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

    /*
     * 7-3.レビュー削除処理
     */
    @DeleteMapping("/deleteReview/{id}-{productId}")
    public ModelAndView delete(@PathVariable int id,
                               @PathVariable int productId) {
        ModelAndView mav = new ModelAndView();
        reviewService.deleteReview(id);
        mav.addObject("id", productId);
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

    /*
     * 7-1.レビュー編集画面表示
     */
    @GetMapping("/reviewEdit/{id}")
    public ModelAndView reviewEdit(@PathVariable String id) {
        ModelAndView mav = new ModelAndView();
        ReviewForm review = reviewService.findReview(Integer.parseInt(id));
        mav.addObject("reviewForm", review);
        mav.setViewName("/reviewEdit");
        return mav;
    }

    /*
     * 7-2.レビュー編集処理
     */
    @PutMapping("/updateReview")
    public ModelAndView updateReview(@AuthenticationPrincipal Account account,
                                     @Validated @ModelAttribute("reviewForm") ReviewForm reviewForm,
                                     BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();

        List<String> errorList = new ArrayList<String>();
        if (bindingResult.hasErrors()) {
            /*for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }*/
            mav.addObject("reviewForm", reviewForm);
            mav.setViewName("/reviewEdit");
            return mav;
        }

        reviewForm.setAccountId(account.getId());
        reviewService.updateReview(reviewForm);

        mav.addObject("id", reviewForm.getProductId());
        mav.setViewName("redirect:/productDetail");
        return mav;
    }

}
