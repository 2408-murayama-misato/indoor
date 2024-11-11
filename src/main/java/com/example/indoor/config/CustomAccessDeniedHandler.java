package com.example.indoor.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * このクラスは
 * ・権限フィルターによって発生した403エラー
 * ・csrfによる403エラー
 * のエラーハンドリングを設定する。
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        HttpSession session = request.getSession();
        //今回は権限フィルターによる403エラーとcsrfによる403エラーの対処は同じにする
        session.setAttribute("errorMessage", "不正なアクセスです");
        response.sendRedirect("/top");
    }
}
