package com.example.indoor.controller.form;

import com.example.indoor.Validation.MaxPrice;
import com.example.indoor.Validation.MinPrice;
import com.example.indoor.Validation.Number;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm {
    @Size(max = 30, message = "30文字以内で入力してください")
    private String keyWord;
    private String category;

    @Number
    @MinPrice
    private Integer minPrice;
    @Number
    @MaxPrice
    private Integer maxPrice;
}
