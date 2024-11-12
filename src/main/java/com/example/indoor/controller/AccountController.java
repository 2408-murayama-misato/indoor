package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.service.AccountService;
import jakarta.servlet.http.HttpSession;
import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

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
    public ModelAndView signup(@Validated(AccountForm.CreateGroup.class) @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        // バリデーション
        List<String> accountErrorList = new ArrayList<String>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                accountErrorList.add(error.getDefaultMessage());
            }

            Account account = new Account();
            account = (Account) accountService.loadUserByUsername(accountForm.getAccount());
            if (account != null) {
                accountErrorList.add("アカウントが重複しています");
            }

            redirectAttributes.addFlashAttribute("accountErrorList", accountErrorList);
            //エラーの際に入力情報が保持されるように
//            mav.addObject("accountForm", accountForm);
            mav.setViewName("redirect:/accountNew");
            return mav;
        }

        accountService.saveAccount(accountForm);
        // ログイン画面へリダイレクトするように修正
        mav.setViewName("redirect:/accountNew");
        return mav;
    }

    /*
     * アカウント編集画面表示
     */
    @GetMapping("/accountEdit")
    public ModelAndView accountEdit() {
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        accountForm = accountService.findById(5);
        //編集画面でパスワードは表示させない
        accountForm.setPassword("");
        mav.addObject("accountForm", accountForm);
        mav.setViewName("/accountEdit");
        return mav;
    }

    /*
     * アカウント編集処理
     */
    @PostMapping("/edit")
    public ModelAndView accountEdit(@Validated(AccountForm.UpdateGroup.class) @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        // バリデーション
        List<String> accountEditErrorList = new ArrayList<String>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                accountEditErrorList.add(error.getDefaultMessage());
            }

            Account account = new Account();
            account = (Account) accountService.loadUserByUsername(accountForm.getAccount());
            if (account != null) {
                accountEditErrorList.add("アカウントが重複しています");
            }

            redirectAttributes.addFlashAttribute("accountEditErrorList", accountEditErrorList);
            //エラーの際に入力情報が保持されるように
//            mav.addObject("accountForm", accountForm);
            mav.setViewName("redirect:/accountEdit");
            return mav;
        }

        //idは渡ってくるようにする
        accountForm.setId(5);
        accountService.updateAccount(accountForm);
        mav.setViewName("redirect:/accountEdit");
        return mav;
    }

    /*
     * アカウント削除処理
     */
    @PostMapping("/deleteAccount")
    public ModelAndView deleteAccount() {
        accountService.deleteAccount(11);
        return new ModelAndView("redirect:/accountNew");
    }

}
