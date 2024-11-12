package com.example.indoor.mapper;

import com.example.indoor.entity.ProductsNotice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductsNoticesMapper {
    void insertProductContact(ProductsNotice productsNoticeEntity);
}
