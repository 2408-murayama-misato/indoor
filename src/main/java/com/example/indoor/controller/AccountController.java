package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.controller.form.SearchForm;
import com.example.indoor.service.AccountService;
import jakarta.servlet.http.HttpSession;
import com.example.indoor.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/login")
    public ModelAndView login(HttpSession session,@ModelAttribute("searchForm") SearchForm searchForm
        ){
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        //セッションからメッセージを取得
        String errorMessage = (String)session.getAttribute("errorMessage");
        mav.setViewName("login");
        mav.addObject("accountForm", accountForm);
        mav.addObject("searchForm", searchForm);
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
    public ModelAndView addAccount(@ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        mav.setViewName("/accountNew");
        mav.addObject("accountForm", accountForm);
        mav.addObject("searchForm", searchForm);
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

            //アカウント重複バリデーション
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
        // ログイン画面へリダイレクト
        mav.setViewName("redirect:/login");
        return mav;
    }

    /*
     * アカウント編集画面表示
     */
    @GetMapping("/accountEdit")
    public ModelAndView accountEdit(@AuthenticationPrincipal Account loginAccount,
                                    @ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView mav = new ModelAndView();
        AccountForm accountForm = new AccountForm();
        accountForm = accountService.findById(loginAccount.getId());
        //編集画面でパスワードは表示させない
        accountForm.setPassword("");
        mav.addObject("accountForm", accountForm);
        mav.addObject("searchForm", searchForm);
        mav.setViewName("/accountEdit");
        return mav;
    }

    /*
     * アカウント編集処理
     */
    @PutMapping("/edit")
    public ModelAndView accountEdit(@Validated(AccountForm.UpdateGroup.class) @ModelAttribute("accountForm") AccountForm accountForm, BindingResult result, @AuthenticationPrincipal Account loginAccount, RedirectAttributes redirectAttributes) {
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

        accountForm.setId(loginAccount.getId());
        accountService.updateAccount(accountForm);
        mav.setViewName("redirect:/accountEdit");
        return mav;
    }

    /*
     * アカウント削除処理
     */
    @DeleteMapping("/deleteAccount")
    public ModelAndView deleteAccount(@AuthenticationPrincipal Account loginAccount) {
        accountService.deleteAccount(loginAccount.getId());
        return new ModelAndView("redirect:/accountNew");
    }

    /*
     * マイページ表示処理
     */
    @GetMapping("/mypage")
    public ModelAndView mypage(@AuthenticationPrincipal Account account,
                               @ModelAttribute("searchForm") SearchForm searchForm
    ) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("account", account);
        mav.setViewName("/mypage");
        mav.addObject("searchForm", searchForm);
        return mav;
    }

//    利用者一覧取得
    @GetMapping("/userList")
    public ModelAndView getUserList (@ModelAttribute("account") String account,
                                     @ModelAttribute("searchForm") SearchForm searchForm
    ) {
        ModelAndView mav = new ModelAndView();
        if (account.length() >20 ) {
            mav.addObject( "errorMessage","アカウント名は２０文字以下で入力してください");
            mav.setViewName("/userList");
        } else {
            List<AccountForm> accounts = accountService.findAllAccount("user", account);
            mav.setViewName("/userList");
            mav.addObject("accounts", accounts);
            mav.addObject("account", account);
            mav.addObject("searchForm", searchForm);
        }
        return mav;
    }
//    販売者一覧取得
    @GetMapping("/sellerList")
    public ModelAndView getSellerList (@ModelAttribute("account") String account,
                                       @ModelAttribute("searchForm") SearchForm searchForm,
                                       RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        if (account.length() >20 ) {
            mav.addObject( "errorMessage","アカウント名は２０文字以下で入力してください");
            mav.setViewName("/sellerList");
        } else {
            List<AccountForm> accounts = accountService.findAllAccount("seller", account);
            mav.setViewName("/sellerList");
            mav.addObject("accounts", accounts);
            mav.addObject("searchForm", searchForm);
        }
        return mav;
    }
//    Userステータス停止処理
    @PutMapping("/stop/{id}")
    public ModelAndView stoppedIsStopped(@PathVariable("id") int id) {
            ModelAndView mav = new ModelAndView();
            accountService.stopAccount(id);
            mav.setViewName("redirect:/userList");
            return mav;
    }

//    Userステータス復活
    @PutMapping("/active/{id}")
    public ModelAndView activeIsStopped(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        accountService.activeAccount(id);
        mav.setViewName("redirect:/userList");
        return mav;
    }

    //    sellerステータス停止処理
    @PutMapping("/sellerStop/{id}")
    public ModelAndView stoppedSeller(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        accountService.stopAccount(id);
        mav.setViewName("redirect:/sellerList");
        return mav;
    }

    //    sellerステータス復活
    @PutMapping("/sellerActive/{id}")
    public ModelAndView activeIsSeller(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView();
        accountService.activeAccount(id);
        mav.setViewName("redirect:/sellerList");
        return mav;
    }
}
