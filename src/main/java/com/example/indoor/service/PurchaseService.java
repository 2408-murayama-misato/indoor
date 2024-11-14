package com.example.indoor.service;

import com.example.indoor.controller.form.CartForm;
import com.example.indoor.controller.form.PurchaseForm;
import com.example.indoor.entity.Purchase;
import com.example.indoor.mapper.PurchasesMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseService {

    @Autowired
    PurchasesMapper purchaseMapper;

    /*
     * 購入情報追加
     */
    public void savePurchases(List<CartForm> cartForms) {

        List<Purchase> savePurchases = setPurchaseEntity(cartForms);
        purchaseMapper.save(savePurchases);
    }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private List<Purchase> setPurchaseEntity(List<CartForm> cartForms) {
        List<Purchase> purchases = new ArrayList<>();

        for (CartForm cartForm : cartForms) {
            Purchase purchase = new Purchase();
            BeanUtils.copyProperties(cartForm, purchase);
            purchases.add(purchase);
        }
        return purchases;
    }

    /*
     * 購入情報取得
     */
    public List<PurchaseForm> findPurchases(Integer id, String start, String end) {

        if (!StringUtils.isBlank(start)) {
            start += " 00:00:00";
        } else {
            start = "2020-01-01 00:00:00.000";
        }

        if (!StringUtils.isBlank(end)) {
            end += " 23:59:59.999";
        } else {
//            Date date = new Date();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            end = dateFormat.format(timestamp).toString();
            end = String.valueOf(timestamp);
        }

        List<Purchase> results = purchaseMapper.findById(id, start, end);
        List<PurchaseForm> purchaseForms = setPurchaseForm(results);
        return purchaseForms;
    }

    /*
     * DBから取得したデータをFormに変換
     */
    private List<PurchaseForm> setPurchaseForm(List<Purchase> results) {
        List<PurchaseForm> purchaseForms = new ArrayList<>();

        for (Purchase result : results) {
            PurchaseForm purchaseForm = new PurchaseForm();
            BeanUtils.copyProperties(result, purchaseForm);
            purchaseForms.add(purchaseForm);
        }
        return purchaseForms;
    }
}
