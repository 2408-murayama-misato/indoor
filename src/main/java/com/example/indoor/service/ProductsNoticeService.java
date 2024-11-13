package com.example.indoor.service;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.ProductsNotice;
import com.example.indoor.mapper.ProductsNoticesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsNoticeService {

    @Autowired
    private ProductsNoticesMapper productsNoticesMapper;

    /*
     * 商品問い合わせ登録処理
     */
    public void createProductContact(ProductsNoticeForm productsNoticeForm, Account account) {
        ProductsNotice productsNoticeEntity = setEntity(productsNoticeForm, account);
        productsNoticesMapper.insertProductContact(productsNoticeEntity);
    }

    private ProductsNotice setEntity(ProductsNoticeForm productsNoticeForm, Account account) {
        ProductsNotice productsNoticeEntity = new ProductsNotice();
        productsNoticeEntity.setText(productsNoticeForm.getText());
        productsNoticeEntity.setFromId(account.getId());
        productsNoticeEntity.setProductId(productsNoticeForm.getProductId());
        productsNoticeEntity.setToId(productsNoticeForm.getToId());
        productsNoticeEntity.setRead(false);
        productsNoticeEntity.setShippedInfo(false);
        return productsNoticeEntity;
    }

    /*
     * 対象の商品問い合わせ取得処理
     */
    public List<ProductsNoticeForm> findProductContacts(int productId) {
        List<ProductsNoticeForm> result = productsNoticesMapper.findProductContacts(productId);
        return result;
    }
}
