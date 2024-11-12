package com.example.indoor.service;

import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Product;
import com.example.indoor.entity.Review;
import com.example.indoor.mapper.ReviewMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    /*
     * 主キー指定でレコードを取得
     */
    public ReviewForm findReview(int id) {
        List<Review> results = new ArrayList<>();
        results.add(reviewMapper.findReview(id));
        List<ReviewForm> reviews = setForm(results);
        return reviews.get(0);
    }
    /*
     * レビューを全件取得
     */
    public List<ReviewForm> findUserReviews() {
        List<Review> results = reviewMapper.findUserReviews();
        List<ReviewForm> reviews = setForm(results);
        return reviews;
    }
    /*
     * EntityをFormにコピー
     */
    private List<ReviewForm> setForm(List<Review> entities) {
        List<ReviewForm> forms = new ArrayList<>();

        for (Review entity : entities) {
            ReviewForm form = new ReviewForm();
            BeanUtils.copyProperties(entity, form);
            forms.add(form);
        }
        return forms;
    }

    /*
     * レコード追加
     */
    public void insertReview(ReviewForm reviewForm) {
        Review saveReview = setEntity(reviewForm);
        reviewMapper.insert(saveReview);
    }

    /*
     * レコード更新
     */
    public void updateReview(ReviewForm reviewForm) {
        Review saveReview = setEntity(reviewForm);
        reviewMapper.update(saveReview);
    }

    /*
     * FormをEntityにコピー
     */
    private Review setEntity(ReviewForm form){
        Review entity = new Review();
        BeanUtils.copyProperties(form, entity);
        return entity;
    }

    /*
     * レコード削除
     */
    public void deleteReview(int id) {
        reviewMapper.delete(id);
    }
}
