package com.example.indoor.mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    public List<Product> findProductDisplay(int id);
    public Product findProductDetail(int id);
    Product findById(@Param("productId") int productId);
    public void insertProduct(Product product);
    public void updateProduct(Product product);
}
