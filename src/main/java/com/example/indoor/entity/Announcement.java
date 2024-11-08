package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Announcement {
    private int id;
    private String text;
    private boolean isRead;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
