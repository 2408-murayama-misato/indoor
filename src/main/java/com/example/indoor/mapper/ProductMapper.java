package com.example.indoor.mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    public Product findProductDetail(int id);
    Product findById(@Param("productId") int productId);

    void updateProductStock(int number, int productId);

    boolean checkStockIsZero(int productId);
}
