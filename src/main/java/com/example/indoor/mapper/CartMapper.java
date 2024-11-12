package com.example.indoor.mapper;

import com.example.indoor.controller.form.CartForm;
import com.example.indoor.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public  interface CartMapper {

    List<Cart> findCarts(@Param("id") int i);

    void addCart(@Param("number") int number,
                 @Param("productId") int productId,
                 @Param("id") int id);

    void countDownCart(@Param("cart") CartForm cartForm);

    void countUpCart(@Param("cart") CartForm cartForm);

    void deleteCart(@Param("id") int id);

    Cart findCart(@Param("productId") int productId,
                  @Param("id") int id);
}
