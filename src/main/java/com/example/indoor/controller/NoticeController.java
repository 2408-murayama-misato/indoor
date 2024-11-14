package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.controller.form.StockNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.service.ProductsNoticeService;
import com.example.indoor.service.StockNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class NoticeController {

    @Autowired
    ProductsNoticeService productsNoticeService;

    @Autowired
    StockNoticeService stockNoticeService;

    /*
     * 画面初期表示
     */
    @GetMapping("/noticeList")
    public ModelAndView noticeList(@AuthenticationPrincipal Account account) {
        ModelAndView mav = new ModelAndView();
        int id = account.getId();
        // 商品問い合わせ、商品発送、商品在庫の通知数をログインしているアカウントのidをもとに取得
        List<ProductsNoticeForm> productContacts = productsNoticeService.findNotReadProductContacts(id);
        mav.addObject("productContacts", productContacts);
        //商品発送通知
        List<ProductsNoticeForm> productShipped = productsNoticeService.findNotReadProductShipped(id);
        mav.addObject("productShipped", productShipped);
        List<ProductsNoticeForm> productShippedForSeller = productsNoticeService.findNotReadProductShippedForSeller(id);
        mav.addObject("productShippedForSeller", productShippedForSeller);
        List<StockNoticeForm> productStock = stockNoticeService.findAllStockNotice(id);
        mav.addObject("productStock", productStock);
        return mav;
    }

    /*
     * 通知既読処理
     */
    @PutMapping("/noticeList/readProductNotice/{id}")
    public ModelAndView saveProductNotice(@PathVariable("id") int id) {
        productsNoticeService.saveReadProductNotice(id);
        return new ModelAndView("redirect:/noticeList");
    }
    /*
     * 商品発送完了通知処理
     */
    @PutMapping("/noticeList/productShipped/{id}")
    public ModelAndView saveProductShippedNotice(@PathVariable("id") int id) {
        productsNoticeService.saveProductShippedNotice(id);
        return new ModelAndView("redirect:/noticeList");
    }
    /*
     * (販売者)在庫通知既読処理
     */
    @PutMapping("/noticeList/productStock/{id}")
    public ModelAndView saveProductShipped(@PathVariable("id") int id) {
        stockNoticeService.saveProductShipped(id);
        return new ModelAndView("redirect:/noticeList");
    }

    /*
     * 通知アーカイブ一覧画面表示
     */
    @GetMapping("/noticeArchive")
    public ModelAndView noticeArchive(@AuthenticationPrincipal Account account) {
        ModelAndView mav = new ModelAndView();
        int id = account.getId();
        // 商品問い合わせ、商品発送、商品在庫の通知数をログインしているアカウントのidをもとに取得
        List<ProductsNoticeForm> productContacts = productsNoticeService.findReadProductContacts(id);
        mav.addObject("productContacts", productContacts);
        //商品発送完了通知
        List<ProductsNoticeForm> productShipped = productsNoticeService.findReadProductShipped(id);
        mav.addObject("productShipped", productShipped);
        //販売者のみ在庫通知取得
        if (Objects.equals(account.getRole(), "seller")) {
            List<StockNoticeForm> productStock = stockNoticeService.findAllReadStockNotice(id);
            mav.addObject("productStock", productStock);
        }
        return mav;
    }
}
