package com.example.indoor.service;

import com.example.indoor.controller.form.StockNoticeForm;
import com.example.indoor.mapper.StockNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockNoticeService {
    @Autowired
    StockNoticeMapper stockNoticeMapper;

    public List<StockNoticeForm> findAllStockNotice(int accountId) {
        List<StockNoticeForm> result = stockNoticeMapper.findAllStockNotice(accountId);
        return result;
    }
}
