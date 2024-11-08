package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FavoriteForm {
    private int id;
    private int productId;
    private int accountId;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
