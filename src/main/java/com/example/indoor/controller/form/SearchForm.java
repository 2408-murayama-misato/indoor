package com.example.indoor.controller.form;



import com.example.indoor.Validation.MaxPrice;
import com.example.indoor.Validation.MinPrice;
import com.example.indoor.Validation.Number;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Getter
@Setter
public class SearchForm {


    @Size(max = 30, message = "30文字以内で入力してください")
    private String keyWord;
    private String category;
    @Number
    @MinPrice
    private String minPrice;
    @Number
    @MaxPrice
    private String maxPrice;
    private String stockCheck;
    private String sort;

    @AssertTrue(message = "最低価格が最高価格より高く設定されています")
    private boolean isPrice() {
        if (isBlank(minPrice) || isBlank(maxPrice)) {
            return true;
        } else {
            return (Integer.parseInt(minPrice) < Integer.parseInt(maxPrice));
        }
    }
}
