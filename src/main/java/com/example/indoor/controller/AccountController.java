package com.example.indoor.controller;

import com.example.indoor.controller.form.AccountForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccountController {
    
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
}
