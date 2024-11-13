package com.example.indoor.mapper;

import com.example.indoor.controller.form.SearchForm;
import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
@Mapper
public interface ProductMapper {
    public Product findProductDetail(int id);
    public Product find(int id);
    public Product findProduct(int id);
    Product findById(@Param("productId") int productId);

    List<Product> findAll(@Param("searchForm") SearchForm searchForm);
}
