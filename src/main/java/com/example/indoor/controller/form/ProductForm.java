package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductForm {
    private int id;
    private int accountId;
    private String name;
    private int price;
    private String category;
    private int stock;
    private boolean isStopped;
    private String description;
    private String imagePass;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    // 内部結合用に追加
    private String accountName;
}
