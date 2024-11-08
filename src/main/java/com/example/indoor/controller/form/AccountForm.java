package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountForm {
    private int id;
    private String account;
    private String password;
    private String name;
    private String address;
    private String credit;
    private String role;
    private int isStopped;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
