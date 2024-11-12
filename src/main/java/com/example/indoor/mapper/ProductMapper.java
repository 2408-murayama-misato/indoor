package com.example.indoor.mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ProductMapper {
    public Product find(int id);
    public Product findProduct(int id);
    Product findById(@Param("productId") int productId);

    List<Product> findAll(@Param("keyWord") String keyWord);
}
