package com.example.indoor.config;

import com.example.indoor.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Autowired
    private AccountService accountService;

    @Autowired //ログイン後の遷移画面をRoleによって分けるファイル
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired //認証が必要なページにログアウト状態でアクセスしたときの設定ファイル
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean //パスワードエンコード
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize

                //以下はログインしていればアクセスできるページの指定。
                .requestMatchers("/noticeList", "/noticeArchive").authenticated()

                //以下は利用者と販売者がアクセス可能のページの指定。ログインアカウントのRoleがuserまたはsellerならばアクセスできる
                .requestMatchers("/contact", "/reviewNew", "/noticeList", "/noticeArchive", "/cart",
                        "/purchase", "/payment", "/mypage", "/orderHistory", "/favorite", "/accountEdit").hasAnyRole("user","seller")

                //以下は販売者のみアクセス可能ページの指定。ログインアカウントのRoleがsellerならばアクセスできる
                .requestMatchers("/productNew", "/productEdit", "/productDisplay").hasRole("seller")

                //以下は管理者のみアクセス可能ページの指定。ログインアカウントのRoleがadminならばアクセスできる
                .requestMatchers("/adminTop", "/contactReply", "/announceNew", "/userList", "/sellerList").hasRole("admin")

                //それ以外はアクセスできるように設定
                .anyRequest().permitAll())

                //フォームをベースにしたログインの設定
                .formLogin(form -> form
                //カスタムログインページのURLを指定
                        .loginPage("/login")
                        .usernameParameter("account")
                        .passwordParameter("password")
                        //ログインが成功したらconfig/CustomAuthenticationSuccessHandlerに書いてある指示に従って遷移する
                        .successHandler(customAuthenticationSuccessHandler))

                //ログアウト処理
                .logout(logout -> logout
                        //ログアウト成功時のリダイレクト先
                        .logoutSuccessUrl("/top")
                        //ログアウト時にセッションを無効にする
                        .invalidateHttpSession(true)
                        //ログアウト時にCookieを無効にする
                        .deleteCookies("JSESSIONID"))

                //エラーハンドリングなど
                .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                );
        return http.build();
    }
}
