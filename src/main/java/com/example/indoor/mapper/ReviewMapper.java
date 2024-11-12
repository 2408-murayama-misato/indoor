package com.example.indoor.mapper;


import com.example.indoor.entity.Review;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ReviewMapper {
    public void insert(Review review);
    public List<Review> findUserReviews();
    public void delete(int id);
}
