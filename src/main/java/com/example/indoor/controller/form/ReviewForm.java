package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewForm {
    private int id;
    private int productId;
    private int accountId;
    private int score;
    private String comment;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    // 内部結合用に追加
    private String accountName;
}
