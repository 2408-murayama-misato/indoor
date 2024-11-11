package com.example.indoor.mapper;

import com.example.indoor.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper {
    public Product find(int id);
}
