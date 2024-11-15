package com.example.indoor.service;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.controller.form.ProductForm;
import com.example.indoor.entity.Cart;
import com.example.indoor.entity.Product;
import com.example.indoor.mapper.AccountMapper;
import com.example.indoor.mapper.CartMapper;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    public final String NO_IMAGE_FILE_PATH = "/img/no-image.png";
    public final String PRODUCT_IMAGE_PATH = "./src/main/resources/static/img/product/";
    public final String IMAGE_RELATIVE_PATH = "/img/product/";

    @Autowired
    CartMapper cartMapper;

    private List<CartForm> setCartsForm(List<Cart> results) {
            List<CartForm> forms = new ArrayList<>();

            for (Cart result : results) {
                CartForm form = new CartForm();
                if (StringUtils.isBlank(result.getProduct().getImagePass())) {
                    result.getProduct().setImagePass(NO_IMAGE_FILE_PATH);
                } else {
                    result.getProduct().setImagePass(IMAGE_RELATIVE_PATH + result.getProduct().getImagePass());
                }
                BeanUtils.copyProperties(result, form);
                forms.add(form);
            }
            return forms;
    }
    private CartForm setCartForm(Cart result) {
        CartForm form = new CartForm();
        if (StringUtils.isBlank(result.getProduct().getImagePass())) {
            result.getProduct().setImagePass(NO_IMAGE_FILE_PATH);
        } else {
            result.getProduct().setImagePass(IMAGE_RELATIVE_PATH + result.getProduct().getImagePass());
        }
        BeanUtils.copyProperties(result, form);
        return form;
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

    public void deletePurchasedCart(int id) {
        cartMapper.deletePurchasedCart(id);
    }
}
