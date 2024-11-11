package com.example.indoor.mapper;

import com.example.indoor.entity.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public  interface CartMapper {

    List<Cart> findCart(@Param("id") int id);
}
