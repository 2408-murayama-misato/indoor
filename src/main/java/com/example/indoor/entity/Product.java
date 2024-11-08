package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Product {
    private int id;
    private int accountId;
    private String name;
    private int value;
    private String category;
    private int stock;
    private boolean isStopped;
    private String description;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
