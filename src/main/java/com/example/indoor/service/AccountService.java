package com.example.indoor.service;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.entity.Account;
import com.example.indoor.mapper.AccountMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountMapper.findByAccount(username);
        return account;
    }

    /*
     * レコード追加
     */
    public void saveAccount(AccountForm reqAccount) {
        Account saveAccount = setAccountEntity(reqAccount);
        // パスワード暗号化処理追記

        accountMapper.save(saveAccount);
    }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private Account setAccountEntity(AccountForm reqAccount) {
        Account account = new Account();
        BeanUtils.copyProperties(reqAccount, account);
        return account;
    }
}