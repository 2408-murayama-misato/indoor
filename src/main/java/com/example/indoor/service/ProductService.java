package com.example.indoor.service;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.ProductsNotice;
import com.example.indoor.mapper.ProductsNoticesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductsNoticesMapper productsNoticesMapper;

    public void createProductContact(ProductsNoticeForm productsNoticeForm, int productId, Account account) {
        ProductsNotice productsNoticeEntity = setEntity(productsNoticeForm, productId, account);
        productsNoticesMapper.insertProductContact(productsNoticeEntity);
    }

    private ProductsNotice setEntity(ProductsNoticeForm productsNoticeForm, int productId, Account account) {
        ProductsNotice productsNoticeEntity = new ProductsNotice();
        productsNoticeEntity.setText(productsNoticeForm.getText());
        productsNoticeEntity.setFromId(account.getId());
        productsNoticeEntity.setProductId(productId);
        productsNoticeEntity.setToId(productsNoticeForm.getToId());
        productsNoticeEntity.setRead(false);
        productsNoticeEntity.setShippedInfo(false);
        return productsNoticeEntity;
    }
}
