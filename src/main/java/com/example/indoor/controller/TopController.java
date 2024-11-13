package com.example.indoor.controller;

import com.example.indoor.controller.form.SearchForm;
import com.example.indoor.entity.Account;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.HttpOutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class TopController {

    @GetMapping("/top")
    public ModelAndView top(@AuthenticationPrincipal @Nullable Account account, HttpSession session,
                            @ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView mav = new ModelAndView();
        String errorMessage = (String)session.getAttribute("errorMessage");
        mav.setViewName("/top");
        mav.addObject("searchForm", searchForm);
        if (account != null) {
            String address = account.getAddress();
        }
        if (errorMessage != null) {
            mav.addObject("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
        return mav;
    }

    @GetMapping("/adminTop")
    public ModelAndView adminTop() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/adminTop");
        return mav;
    }
}
