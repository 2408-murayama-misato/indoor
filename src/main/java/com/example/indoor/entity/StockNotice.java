package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class StockNotice {
    private int id;
    private String text;
    private int productId;
    private int sellerId;
    private boolean isRead;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
