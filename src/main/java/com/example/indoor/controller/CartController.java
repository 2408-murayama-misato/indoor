package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.CartForm;
import com.example.indoor.controller.form.SearchForm;
import com.example.indoor.entity.Account;
import com.example.indoor.entity.Product;
import com.example.indoor.mapper.AccountMapper;
import com.example.indoor.mapper.ProductMapper;
import com.example.indoor.service.CartService;
import com.example.indoor.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isBlank;

@Controller
public class CartController {



   @Autowired
    CartService cartService;

   @Autowired
   ProductService productService;

   @Autowired
   AccountMapper accountMapper;

   @Autowired
   ProductMapper productMapper;



   @GetMapping("/cart")
    public ModelAndView findCart(@AuthenticationPrincipal Account loginAccount,
                                 @ModelAttribute("searchForm") SearchForm searchForm
    ) {
       ModelAndView mav = new ModelAndView();

//       カートマスタ商品取得
       List<CartForm> cartForms = cartService.findCart(loginAccount.getId());
       mav.setViewName("/cart");
       mav.addObject("cartForms", cartForms);
       mav.addObject("searchForm", searchForm);
       return mav;
   }

   @PostMapping("/addCart/{productId}")
   public  ModelAndView addCart(@PathVariable String productId,
                                @ModelAttribute("number") String number,
                                @AuthenticationPrincipal Account loginAccount,
                                RedirectAttributes redirectAttributes) {
      ModelAndView mav = new ModelAndView();
      List<String> errorMessages = new ArrayList<>();
      if (loginAccount == null) {
         mav.setViewName("redirect:/login");
         redirectAttributes.addFlashAttribute("errorMessage", "ログイン後カートに追加してください");
      } else {
//   商品情報取得
         Product product = productMapper.findById(Integer.parseInt(productId));
//      パラメータチェック
         if (!productId.matches("^[0-9]*$") || !number.matches("^[0-9]*$")) {
            errorMessages.add("不正なパラメータが入力されました");
         }
//      在庫数と注文数比較
         if (number.matches("^[0-9]*$") && !isBlank(number)) {
            if (product.getStock() < Integer.parseInt(number)) {
               errorMessages.add("注文数が在庫数を上回りました。注文数を減らして再度カートに入れてください");
            }
         }
         if (isBlank(number)) {
            errorMessages.add("数値を入力してください");
         }
         if (errorMessages.size() > 0) {
            redirectAttributes.addFlashAttribute("errorMessages", errorMessages);
            redirectAttributes.addFlashAttribute("id", product.getId());
            redirectAttributes.addFlashAttribute("number", number);
            mav.setViewName("redirect:/productDetail");
         } else {
            cartService.addCart(Integer.parseInt(number), (Integer.parseInt(productId)), loginAccount.getId());
            redirectAttributes.addFlashAttribute("resultMessage", product.getName() + "がカートに追加されました");
            mav.setViewName("redirect:/cart");
         }
      }
      return mav;
   }

//   カート注文数マイナス
   @PutMapping("/countDownCart/{id}/{number}")
   public ModelAndView countDownCart (@ModelAttribute CartForm cartForm) {
      ModelAndView mav = new ModelAndView();
      cartService.countDownCart(cartForm);
      mav.setViewName("redirect:/cart");
      return mav;
   }

//   カートの注文数プラス
   @PutMapping("/countUpCart/{id}/{number}/{productId}")
   public ModelAndView countUpCart (@ModelAttribute CartForm cartForm,
                                    RedirectAttributes redirectAttributes) {
      ModelAndView mav = new ModelAndView();
      String errorMessage;
      Product product = productMapper.findById(cartForm.getProductId());
      if (product.getStock() < cartForm.getNumber() +1) {
         errorMessage = "注文数が在庫数を上回るため、これ以上追加できません";
         mav.setViewName("redirect:/cart");
         redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
         redirectAttributes.addFlashAttribute("errorMessageId", cartForm.getId());
      } else {
         cartService.countUpCart(cartForm);
         mav.setViewName("redirect:/cart");
      }
      return mav;
   }

//   カートの削除
   @DeleteMapping("/deleteCart/{id}")
   public ModelAndView deleteCart (@PathVariable int id) {
      ModelAndView mav = new ModelAndView();
      cartService.deleteCart(id);
      mav.setViewName("redirect:/cart");
      return mav;
   }
}