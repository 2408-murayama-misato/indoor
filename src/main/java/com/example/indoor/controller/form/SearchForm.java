package com.example.indoor.controller.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm {
    @Size(max = 30, message = "30文字以内で入力してください")
    private String keyWord;
    private String category;
    @Pattern(regexp = "^\\d+$", message = "数字以外が入力されています")
    private String minPrice;
    @Pattern(regexp = "^\\d+$", message = "数字以外が入力されています")
    private String maxPrice;
}
