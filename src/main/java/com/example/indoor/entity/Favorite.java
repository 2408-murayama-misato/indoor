package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Favorite {
    private int id;
    private int productId;
    private int accountId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
