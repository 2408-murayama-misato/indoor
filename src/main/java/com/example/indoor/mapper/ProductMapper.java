package com.example.indoor.mapper;

import com.example.indoor.entity.Product;
import com.example.indoor.entity.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    public Product findProductDetail(int id);
    Product findById(@Param("productId") int productId);
    public void insertProduct(Product product);
}
