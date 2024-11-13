package com.example.indoor.mapper;

import com.example.indoor.controller.form.PurchaseForm;
import com.example.indoor.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchasesMapper {
    void save(PurchaseForm.Purchase purchase);
}
