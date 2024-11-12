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
    CartMapper cartMapper;

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
            cartForm.setProduct(cart.getProduct());
            cartForm.setProductsImage(cart.getProductsImage());
            cartForms.add(cartForm);
        }
        return cartForms;
    }
    private CartForm setCartForm(Cart result) {
            CartForm cartForm = new CartForm();
            cartForm.setId(result.getId());
            cartForm.setProductId(result.getProductId());
            cartForm.setAccountId(result.getAccountId());
            cartForm.setNumber(result.getNumber());
            cartForm.setCreatedDate(result.getCreatedDate());
            cartForm.setUpdatedDate(result.getUpdatedDate());
            cartForm.setProduct(result.getProduct());
            cartForm.setProductsImage(result.getProductsImage());
        return cartForm;
    }

    public List<CartForm> findCart(int i) {
        List<Cart> results = cartMapper.findCarts(i);
        List<CartForm> cart = setCartsForm(results);
        return cart;
    }

    public void addCart(int number, int productId, int id) {
         Cart cart = cartMapper.findCart(productId,id);
         if (cart == null) {
             cartMapper.addCart(number, productId, id);
         } else {
             countUpCart(setCartForm(cart));
         }
    }

    //   カート注文数マイナス
    public void countDownCart(CartForm cartForm) {
        cartMapper.countDownCart(cartForm);
    }

    //   カート注文数プラス
    public void countUpCart(CartForm cartForm) {
        cartMapper.countUpCart(cartForm);
    }

    public void deleteCart(int id) {
        cartMapper.deleteCart(id);
    }

    //購入画面用のカート情報取得
    public void List<CartForm> findCartPurchase {
        List<Cart> results = cartMapper.findCartPurchases;
        List<CartForm> cart = setCartsForm(results);
        return cart;

    }
}
