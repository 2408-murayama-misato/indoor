package com.example.indoor.service;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ProductImageForm;
import com.example.indoor.entity.Product;
import com.example.indoor.entity.ProductImage;
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

    final String NO_IMAGE_FILE_PATH = "/img/no-image.png";

    public List<ProductForm> findProductDisplay(int id) {
        List<Product> results = productMapper.findProductDisplay(id);
        List<ProductForm> products = setForm(results);
        return products;
    }
    /*
     * 主キー指定で商品レコードを取得
     */
    public ProductForm findProduct(int id) {
        List<Product> results = new ArrayList<>();
        results.add(productMapper.findProductDetail(id));
        List<ProductForm> products = setForm(results);

        return products.get(0);
    }
    /*
     * Product entityをProduct formにコピー
     */
    private List<ProductForm> setForm(List<Product> entities) {
        List<ProductForm> forms = new ArrayList<>();

        for (Product entity : entities) {
            ProductForm form = new ProductForm();
            BeanUtils.copyProperties(entity, form);
            // ファイルパスを修正
            if (form.getImagePass().isBlank()) {
                form.setImagePass(NO_IMAGE_FILE_PATH);
            } else {
                form.setImagePass("/img/product/" + form.getImagePass());
            }
            forms.add(form);
        }
        return forms;
    }
    /*
     * 商品レコード追加
     */
    public void insertProduct(ProductForm productForm) {
        Product saveProduct = setProductEntity(productForm);
        productMapper.insertProduct(saveProduct);
    }
    /*
     * Product formをEntityにコピー
     */
    private Product setProductEntity(ProductForm form){
        Product entity = new Product();
        BeanUtils.copyProperties(form, entity);
        return entity;
    }
}
