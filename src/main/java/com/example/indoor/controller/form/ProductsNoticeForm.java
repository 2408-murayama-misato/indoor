package com.example.indoor.controller.form;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductsNoticeForm {
    private int id;

    @NotNull(message = "本文を入力してください") //NotBlankアノテーション作ったら差し替える
    private String text;

    private int fromId;

    private int productId;

    private int toId;

    private boolean isRead;

    private boolean isShippedInfo;

    private Timestamp createdDate;
    private Timestamp updatedDate;

    //inner join用
    private String fromName;
}
