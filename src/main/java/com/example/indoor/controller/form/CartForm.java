package com.example.indoor.controller.form;

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
}
