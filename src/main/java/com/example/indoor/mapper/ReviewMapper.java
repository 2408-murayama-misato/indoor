package com.example.indoor.mapper;


import com.example.indoor.entity.Review;

@org.apache.ibatis.annotations.Mapper
public interface ReviewMapper {
    public void insert(Review review);
}
