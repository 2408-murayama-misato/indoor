package com.example.indoor.controller.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AccountForm {

    public interface CreateGroup {}
    public interface UpdateGroup {}

    private int id;

    @NotBlank(groups = CreateGroup.class, message = "アカウントを入力してください")
    @Pattern(groups = {CreateGroup.class, UpdateGroup.class}, regexp = "^[a-zA-Z0-9]{6,20}+$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;

    @NotBlank(groups = CreateGroup.class, message = "パスワードを入力してください")
    @Pattern(groups = {CreateGroup.class, UpdateGroup.class}, regexp = "^[a-zA-Z0-9!-/:-@\\[-`{-~]{6,20}+$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    private String password;

    @NotBlank(groups = CreateGroup.class,message = "氏名を入力してください")
    @Size(groups = {CreateGroup.class, UpdateGroup.class}, max = 10, message = "氏名は10文字以下で入力してください")
    private String name;
    private String address;
    private String credit;
    private String role;
    private int isStopped;
    private Timestamp createdDate;
    private Timestamp updatedDate;

    // ユーザー登録用に追記
    private String passwordRetype;

    public @AssertTrue(groups = {CreateGroup.class, UpdateGroup.class}, message = "パスワードと確認用パスワードが一致しません") boolean isEquals() {return password.equals(passwordRetype);}
}
