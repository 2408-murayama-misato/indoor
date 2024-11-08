package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AnnouncementForm {
    private int id;
    private String text;
    private boolean isRead;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
