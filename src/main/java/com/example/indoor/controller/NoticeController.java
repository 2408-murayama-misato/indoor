package com.example.indoor.controller;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.controller.form.StockNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.ProductsNotice;
import com.example.indoor.service.ProductsNoticeService;
import com.example.indoor.service.StockNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
public class NoticeController {

    @Autowired
    ProductsNoticeService productsNoticeService;

    @Autowired
    StockNoticeService stockNoticeService;

    @GetMapping("/noticeList")
    public ModelAndView noticeList(@AuthenticationPrincipal Account account) {
        ModelAndView mav = new ModelAndView();
        int id = account.getId();
        // 商品問い合わせ、商品発送、商品在庫の通知数をログインしているアカウントのidをもとに取得
        List<ProductsNoticeForm> productContacts = productsNoticeService.findNotReadProductContacts(id);
        mav.addObject("productContacts", productContacts);
        //商品発送通知
        if (Objects.equals(account.getRole(), "user")) {
            List<ProductsNoticeForm> productShipped = productsNoticeService.findNotReadProductShippedForUser(id);
            mav.addObject("productShipped", productShipped);
        } else { //ログインアカウントが販売者の場合
            List<ProductsNoticeForm> productShipped = productsNoticeService.findNotReadProductShippedForSeller(id);
            mav.addObject("productShipped", productShipped);
            List<StockNoticeForm> productStock = stockNoticeService.findAllStockNotice(id);
            mav.addObject("productStock", productStock);
        }

        return mav;
    }
}
