package com.example.indoor.service;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.PurchaseForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.Purchase;
import com.example.indoor.mapper.AccountMapper;
import com.example.indoor.mapper.PurchasesMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchasesMapper purchaseMapper;

    /*
     * アカウント情報追加
     */
    public void savePurchase(PurchaseForm reqPurchase) {

        Purchase savePurchase = setPurchaseEntity(reqPurchase);
        purchaseMapper.save(savePurchase);
    }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private Purchase setPurchaseEntity(PurchaseForm reqPurchase) {
        Purchase purchase = new Purchase();
        BeanUtils.copyProperties(reqPurchase, purchase);
        return purchase;
    }
}
