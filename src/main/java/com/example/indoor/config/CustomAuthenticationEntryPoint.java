package com.example.indoor.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
 * このクラスはSpringSecurityによって、
 * 認証済みでないと閲覧ができないページにアクセスが
 * あった際にエラーメッセージを追加や遷移先指定の設定をする。
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //セッションにエラーメッセージを追加
        HttpSession session = request.getSession();
        session.setAttribute("ErrorMessage", "ログインしてください");
        response.sendRedirect(request.getContextPath() + "/login?loginFilter=true");
    }
}
