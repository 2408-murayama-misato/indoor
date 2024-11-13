package com.example.indoor.controller.form;

import com.example.indoor.validation.CheckBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProductsNoticeForm {
    private int id;

    @CheckBlank(message = "本文を入力してください")
    @Size(max = 200, message = "本文は200文字以内で入力してください")
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
    private String productName;
}
