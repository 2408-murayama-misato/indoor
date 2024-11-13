package com.example.indoor.controller.form;



import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm {
    @Size(max = 30, message = "30文字以内で入力してください")
    private String keyWord;
    private String category;
    private String minPrice;
    private String maxPrice;
    private String stockCheck;
}
