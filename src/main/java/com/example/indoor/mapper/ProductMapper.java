package com.example.indoor.mapper;


import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Param;

@org.apache.ibatis.annotations.Mapper
public interface ProductMapper {
    Product findById(@Param("productId") int productId);
}
