package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductImage {
    private int id;
    private int productId;
    private String pass;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
