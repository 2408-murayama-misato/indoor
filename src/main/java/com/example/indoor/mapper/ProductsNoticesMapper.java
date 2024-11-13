package com.example.indoor.mapper;

import com.example.indoor.controller.form.ProductsNoticeForm;
import com.example.indoor.entity.ProductsNotice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductsNoticesMapper {
    void insertProductContact(ProductsNotice productsNoticeEntity);

    List<ProductsNoticeForm> findAllProductContacts(int productId);

    List<ProductsNoticeForm> findNotReadProductContacts(int accountId);

    List<ProductsNoticeForm> findNotReadProductShippedForUser(int accountId);

    List<ProductsNoticeForm> findNotReadProductShippedForSeller(int accountId);
}
