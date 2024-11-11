package com.example.indoor.service;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.entity.Product;
import com.example.indoor.mapper.ProductMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    ProductMapper productMapper;
    
    /*
     * 商品ID指定で商品レコードを取得
     */
    public ProductForm findProduct(int id) {
        List<Product> results = new ArrayList<>();
        results.add(productMapper.find(id));
        List<ProductForm> products = setProductForm(results);

        return products.get(0);
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<ProductForm> setProductForm(List<Product> results) {
        List<ProductForm> products = new ArrayList<>();

        for (Product result : results) {
            ProductForm product = new ProductForm();
            BeanUtils.copyProperties(result, product);
            products.add(product);
        }
        return products;
    }
}
