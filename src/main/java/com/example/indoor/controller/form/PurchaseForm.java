package com.example.indoor.controller.form;

import com.example.indoor.entity.Product;
import com.example.indoor.entity.Purchase;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseForm {
        private int id;
        private int productId;
        private int accountId;
        private int price;
        private int number;
        private String category;
        private Timestamp createdDate;
        private Timestamp updatedDate;

        private Product product;
}
