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
    public List<ProductsNoticeForm> findAllProductContacts(int productId) {
        List<ProductsNoticeForm> result = productsNoticesMapper.findAllProductContacts(productId);
        return result;
    }

    /*
     * 通知一覧画面に表示する
     * ログインアカウントが対象の未読の商品問い合わせを取得
     */
    public List<ProductsNoticeForm> findNotReadProductContacts(int accountId) {
        List<ProductsNoticeForm> result = productsNoticesMapper.findNotReadProductContacts(accountId);
        return result;
    }
    /*
     * 利用者権限かつログインアカウントが対象の未読の商品発送通知を取得
     */
    public List<ProductsNoticeForm> findNotReadProductShippedForUser(int accountId) {
        List<ProductsNoticeForm> result = productsNoticesMapper.findNotReadProductShippedForUser(accountId);
        return result;
    }
    /*
     * 販売者権限かつログインアカウントが対象の未読の商品発送通知を取得
     */
    public List<ProductsNoticeForm> findNotReadProductShippedForSeller(int accountId) {
        List<ProductsNoticeForm> result = productsNoticesMapper.findNotReadProductShippedForSeller(accountId);
        return result;
    }
}
