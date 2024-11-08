package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PurchaseForm {
    private int id;
    private int productId;
    private int accountId;
    private int value;
    private int number;
    private String category;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
