package com.example.indoor.mapper;

import com.example.indoor.controller.form.StockNoticeForm;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface StockNoticeMapper {
    List<StockNoticeForm> findAllStockNotice(int accountId);

    void saveProductShipped(int id);

    void insertStockNotice(int productId);

    List<StockNoticeForm> findAllReadStockNotice(int accountId);
}
