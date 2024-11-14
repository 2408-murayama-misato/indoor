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
import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductService;
import com.example.indoor.service.ReviewService;
import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.service.ProductsNoticeService;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    ProductsNoticeService productsNoticeService;

    @Autowired
    ReviewService reviewService;

    /*
     * 5-1.商品詳細画面表示
     */
    @GetMapping("/productDetail")
    public ModelAndView productDetail(@ModelAttribute("id") String id, @AuthenticationPrincipal Account account,
                                      @ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        List<ReviewForm> reviews = reviewService.findUserReviews();
        List<ProductsNoticeForm> productContacts = productsNoticeService.findAllProductContacts(Integer.parseInt(id));
        ProductsNoticeForm productsNoticeForm = new ProductsNoticeForm();
        mav.addObject("product", product);
        mav.addObject("reviews", reviews);
        mav.addObject("productsNoticeForm", productsNoticeForm);
        mav.addObject("productContacts", productContacts);
        mav.addObject("searchForm", searchForm);
        mav.addObject("account", account);

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
            List<ProductsNoticeForm> productContacts = productsNoticeService.findAllProductContacts(id);
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
    public ModelAndView reviewNew(@PathVariable String id,
                                  @ModelAttribute("searchForm") SearchForm searchForm
                                  ) {
        ModelAndView mav = new ModelAndView();

        ProductForm product = productService.findProduct(Integer.parseInt(id));
        mav.addObject("product", product);
        mav.addObject("searchForm", searchForm);

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

        if (bindingResult.hasErrors()) {
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

        if (bindingResult.hasErrors()) {
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
    /*
     * 9-1.出品商品画面表示
     */
    @GetMapping("/productDisplay")
    public ModelAndView productDisplay(@AuthenticationPrincipal Account account) {
        ModelAndView mav = new ModelAndView();
        List<ProductForm> products = productService.findProductDisplay(account.getId());
        mav.addObject("products", products);
        mav.setViewName("/productDisplay");
        return mav;
    }
    /*
     * 10-1.商品登録画面表示
     */
    @GetMapping("/productNew")
    public ModelAndView productNew() {
        ModelAndView mav = new ModelAndView();
        ProductForm productForm = new ProductForm();
        mav.addObject("productForm", productForm);
        mav.setViewName("/productNew");
        return mav;
    }
    /*
     * 10-2.商品登録処理
     */
    @PutMapping("/addProduct")
    public ModelAndView addProduct(@AuthenticationPrincipal Account account,
                                   @Validated @ModelAttribute("productForm") ProductForm productForm,
                                   BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mav.addObject("productForm", productForm);
            mav.setViewName("/productNew");
            return mav;
        }

        // サーバーに商品イメージ画像を保存して、ファイル名をformにセット
        for (MultipartFile file : productForm.getImageFile()) {
            try {
                if (!StringUtils.isBlank(file.getOriginalFilename())) {
                    String fileName = productService.getUploadFileName(file.getOriginalFilename());
                    productService.saveFile(file, fileName);
                    // ファイルパスを保存
                    productForm.setImagePass(fileName);
                }
            } catch (IOException e) {
                // エラー処理は省略
            }
        }

        productForm.setAccountId(account.getId());
        productService.insertProduct(productForm);

        mav.setViewName("redirect:/productDisplay");
        return mav;
    }


    /*
     * 9-2．出品商品状態変更処理
     */
    @PutMapping("/changeProductIsStopped-{id}")
    public ModelAndView changeIsStopped(@PathVariable Integer id) {

        ProductForm saveProduct = productService.findProduct(id);
        // isStoppedを反転
        saveProduct.setStopped(!saveProduct.isStopped());
        productService.updateProduct(saveProduct);
        return new ModelAndView("redirect:/productDisplay");
    }

    /*
     * 11-1.商品編集画面表示
     */
    @GetMapping("/productEdit-{id}")
    public ModelAndView productNew(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();
        ProductForm productForm = productService.findProduct(id);
        mav.addObject("productForm", productForm);
        mav.setViewName("/productEdit");
        return mav;
    }
    /*
     * 11-2.商品編集処理
     */
    @PutMapping("/editProduct")
    public ModelAndView editProduct(@AuthenticationPrincipal Account account,
                                    @Validated @ModelAttribute("productForm") ProductForm productForm,
                                    BindingResult bindingResult) {

        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            mav.addObject("productForm", productForm);
            mav.setViewName("/productNew");
            return mav;
        }

        ProductForm refProduct = productService.findProduct(productForm.getId());

        // サーバーに商品イメージ画像を保存して、ファイル名をformにセット
        for (MultipartFile file : productForm.getImageFile()) {
            try {
                if (!StringUtils.isBlank(file.getOriginalFilename())) {
                    String fileName = productService.getUploadFileName(file.getOriginalFilename());
                    productService.saveFile(file, fileName);
                    // ファイルパスを保存
                    productForm.setImagePass(fileName);
                } else if (!refProduct.getImagePass().equals(productService.NO_IMAGE_FILE_PATH)){
                    String imagePass = refProduct.getImagePass();
                    productForm.setImagePass(imagePass.substring(productService.IMAGE_RELATIVE_PATH.length()).toLowerCase());
                }
            } catch (IOException e) {
                // エラー処理は省略
            }
        }

        productForm.setStopped(refProduct.isStopped());
        productService.updateProduct(productForm);

        mav.setViewName("redirect:/productDisplay");
        return mav;
    }

//    商品検索
    @GetMapping("/search")
    public ModelAndView searchProduct(@Validated @ModelAttribute("searchForm") SearchForm searchForm,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        List<String> errorMessages = new ArrayList<>();
        if (isBlank(searchForm.getKeyWord()) && isBlank(searchForm.getCategory())) {
            mav.setViewName("redirect:/top");
            return mav;
        }
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        if (errorMessages.size() > 0) {
            mav.addObject("errorMessages",errorMessages);
            mav.addObject("searchForm", searchForm);
            mav.setViewName("/productList");
        } else {
        List<ProductForm> products = productService.findAllProduct(searchForm);
        mav.addObject("productForm", products);
        mav.addObject("searchForm", searchForm);
        mav.setViewName("/productList");
        }
        return mav;

    }
}
