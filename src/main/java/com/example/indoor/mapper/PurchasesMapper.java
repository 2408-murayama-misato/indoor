package com.example.indoor.mapper;

import com.example.indoor.controller.form.PurchaseForm;
import com.example.indoor.entity.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchasesMapper {
    void save(Purchase purchase);
    List<Purchase> findById(Integer id, @Param("start") String start, @Param("end") String end);
}
