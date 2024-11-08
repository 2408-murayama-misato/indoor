package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductImageForm {
    private int id;
    private int productId;
    private String pass;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
