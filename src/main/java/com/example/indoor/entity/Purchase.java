package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Purchase {
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
