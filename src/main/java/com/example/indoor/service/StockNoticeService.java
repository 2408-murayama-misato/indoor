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

    /*
     * 通知取得処理
     */
    public List<StockNoticeForm> findAllStockNotice(int accountId) {
        List<StockNoticeForm> result = stockNoticeMapper.findAllStockNotice(accountId);
        return result;
    }

    /*
     * 通知既読処理
     */
    public void saveProductShipped(int id) {
        stockNoticeMapper.saveProductShipped(id);
    }

    /*
     * アーカイブ通知取得処理
     */
    public List<StockNoticeForm> findAllReadStockNotice(int accountId) {
        List<StockNoticeForm> result = stockNoticeMapper.findAllReadStockNotice(accountId);
        return result;
    }
}
