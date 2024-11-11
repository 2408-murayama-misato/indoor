package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.service.AccountService;
import jakarta.servlet.http.HttpSession;
import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public ModelAndView login(HttpSession session){
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        //セッションからメッセージを取得
        String errorMessage = (String)session.getAttribute("ErrorMessage");
        mav.setViewName("login");
        mav.addObject("accountForm", accountForm);
        if (errorMessage != null) {
            mav.addObject("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
        return mav;
    }

    /*
     * 会員登録画面表示
     */
    @GetMapping("/accountNew")
    public ModelAndView addAccount() {
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        mav.setViewName("/accountNew");
        mav.addObject("accountForm", accountForm);
        return mav;
    }

    /*
     * 会員登録処理
     */
    @PostMapping("/signup")
    public ModelAndView signup(@Validated @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        accountService.saveAccount(accountForm);
        // ログイン画面へリダイレクト
        mav.setViewName("redirect:/");
        return mav;
    }
}
