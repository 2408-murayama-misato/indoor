package com.example.indoor.controller.form;

import com.example.indoor.entity.Purchase;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseForm {
    //コンストラクタ 不要なら消す
//    public PurchaseForm(int productId, int accountId, int price, int number) {
//        this.productId = productId;
//        this.accountId = accountId;
//        this.price = price;
//        this.number = number;
//    }

    private List<Purchase> purchases;

    public static class Purchase {
    private int id;
    private int productId;
    private int accountId;
    private int price;
    private int number;
    private String category;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    }
}
