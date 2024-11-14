package com.example.indoor.mapper;

import com.example.indoor.controller.form.SearchForm;
import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.List;
@Mapper
public interface ProductMapper {
    public List<Product> findProductDisplay(int id);
    public Product findProductDetail(int id);
    public Product find(int id);
    public Product findProduct(int id);
    Product findById(@Param("productId") int productId);
    void updateProductStock(int number, int productId);
    boolean checkStockIsZero(int productId);
    public void insertProduct(Product product);
    public void updateProduct(Product product);

    List<Product> findAll(@Param("searchForm") SearchForm searchForm);
}
