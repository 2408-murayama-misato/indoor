package com.example.indoor.controller.form;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Map.entry;

@Getter
@Setter
public class ProductForm {
    private int id;
    private int accountId;
    private String name;
    private int price;
    private String category;
    private int stock;
    private boolean isStopped;
    private String description;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    // 内部結合用に追加
    private String accountName;
    private String imagePass;
    // 商品イメージ
    private List<MultipartFile> imageFile;
    // カテゴリー管理用に追加
    private int categoryId;
    private Map<Integer, String> categoryMap = Map.ofEntries(
            entry(1, "T-Shirts"),
            entry(2, "Bottoms"),
            entry(3, "Cap"),
            entry(4, "Shoes"),
            entry(5, "Outer")
    );
    /*
     * カテゴリーのvalueを取得
     */
    public String getStatusLabel(int categoryId) {
        for (Map.Entry<Integer, String> entry : this.categoryMap.entrySet()) {
            if (categoryId == entry.getKey()) {
                return entry.getValue();
            }
        }
        return "";
    }
    /*
     * カテゴリーのkeyを取得
     */
    public Integer translationToState(String category) {
        for (Map.Entry<Integer, String> entry : this.categoryMap.entrySet()) {
            if (Objects.equals(category, entry.getValue())) {
                return entry.getKey();
            }
        }
        return 0;
    }
}
