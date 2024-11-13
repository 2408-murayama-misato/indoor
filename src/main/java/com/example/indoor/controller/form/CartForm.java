package com.example.indoor.controller.form;

import com.example.indoor.entity.Account;
import com.example.indoor.entity.Product;
import com.example.indoor.entity.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CartForm {
    private int id;
    private int productId;
    private int accountId;
    private int number;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Product product;
    private ProductImage productsImage;
}
