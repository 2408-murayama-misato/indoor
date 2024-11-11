package com.example.indoor.service;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.entity.Cart;
import com.example.indoor.mapper.AccountMapper;
import com.example.indoor.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    public List<CartForm> findCart(AccountForm loginAccount) {
        List<Cart> results = cartMapper.findCart(loginAccount.getId());
        List<CartForm> cartForms = setCartsForm(results);
        return cartForms;
    }

    private List<CartForm> setCartsForm(List<Cart> results) {
        List<CartForm> cartForms = new ArrayList<>();
        for (Cart cart : results) {
            CartForm cartForm = new CartForm();
            cartForm.setId(cart.getId());
            cartForm.setProductId(cart.getProductId());
            cartForm.setAccountId(cart.getAccountId());
            cartForm.setNumber(cart.getNumber());
            cartForm.setCreatedDate(cart.getCreatedDate());
            cartForm.setUpdatedDate(cart.getUpdatedDate());
            cartForms.add(cartForm);
        }
        return cartForms;
    }
}
