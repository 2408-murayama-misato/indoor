package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductsNotice {
    private int id;
    private String text;
    private int fromId;
    private int productId;
    private int toId;
    private boolean isRead;
    private boolean isShippedInfo;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
