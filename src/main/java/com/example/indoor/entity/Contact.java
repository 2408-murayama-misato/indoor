package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Contact {
    private int id;
    private String text;
    private int fromId;
    private int toId;
    private boolean isRead;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
