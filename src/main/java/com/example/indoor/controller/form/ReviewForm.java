package com.example.indoor.controller.form;

import com.example.indoor.validation.CheckBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ReviewForm {
    private int id;
    private int productId;
    private int accountId;
    private int score;

    @CheckBlank(message = "本文を入力してください")
    @Size(max = 200, message = "本文は200文字以内で入力してください")
    private String comment;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    // 内部結合用に追加
    private String accountName;
}
