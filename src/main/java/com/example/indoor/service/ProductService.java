package com.example.indoor.service;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.Product;
import com.example.indoor.entity.ProductsNotice;
import com.example.indoor.mapper.ProductMapper;
import com.example.indoor.mapper.ProductsNoticesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    private ProductsNoticesMapper productsNoticesMapper;

    /*
     * 主キー指定で商品レコードを取得
     */
    public ProductForm findProduct(int id) {
        List<Product> results = new ArrayList<>();
        results.add(productMapper.findProduct(id));
        List<ProductForm> products = setForm(results);

        return products.get(0);
    }
    /*
     * EntityをFormにコピー
     */
    private List<ProductForm> setForm(List<Product> entities) {
        List<ProductForm> forms = new ArrayList<>();

        for (Product entity : entities) {
            ProductForm form = new ProductForm();
            BeanUtils.copyProperties(entity, form);
            forms.add(form);
        }
        return forms;
    }

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
