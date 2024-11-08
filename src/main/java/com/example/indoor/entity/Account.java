package com.example.indoor.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Account {
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
