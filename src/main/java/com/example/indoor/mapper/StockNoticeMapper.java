package com.example.indoor.mapper;

import com.example.indoor.controller.form.StockNoticeForm;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface StockNoticeMapper {
    List<StockNoticeForm> findAllStockNotice(int accountId);
}
