package com.example.indoor.service;

import com.example.indoor.controller.form.ReviewForm;
import com.example.indoor.entity.Review;
import com.example.indoor.mapper.ReviewMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    ReviewMapper reviewMapper;

    /*
     * レコード追加
     */
    public void insertReview(ReviewForm reviewForm) {
        Review saveReview = setEntity(reviewForm);
        reviewMapper.insert(saveReview);
    }

    /*
     * FormをEntityにコピー
     */
    private Review setEntity(ReviewForm form){
        Review entity = new Review();
        BeanUtils.copyProperties(form, entity);
        return entity;
    }
}
