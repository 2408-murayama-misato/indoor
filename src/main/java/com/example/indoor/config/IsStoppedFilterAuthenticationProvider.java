package com.example.indoor.config;

import com.example.indoor.entity.Account;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * ログインしたアカウントがisStopped == 1で停止中だった場合
 * ログインを阻止するフィルターのようなファイルです
 */
public class IsStoppedFilterAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        //UserDetailsを取得
        UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);
        if(!(userDetails instanceof Account)) {
            throw new BadCredentialsException("アカウントまたはパスワードが違います");
        }
        Account account = (Account) userDetails;

        // isStoppedフラグを確認する
        if (account.getIsStopped() != 0) {
            throw new BadCredentialsException("アカウントまたはパスワードが違います");
        }

        //問題ない場合の処理
        return super.authenticate(authentication);
    }
}
