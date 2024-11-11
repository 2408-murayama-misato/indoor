package com.example.indoor.controller;

import jakarta.servlet.http.HttpSession;
import org.apache.coyote.http11.HttpOutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class TopController {

    @GetMapping("/top")
    public ModelAndView top(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        String errorMessage = (String)session.getAttribute("errorMessage");
        mav.setViewName("/top");
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

    @GetMapping("/mypage")
    public ModelAndView mypage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/mypage");
        return  mav;
    }
}
