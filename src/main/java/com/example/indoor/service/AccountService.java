package com.example.indoor.service;

import com.example.indoor.controller.form.AccountForm;
import com.example.indoor.entity.Account;
import com.example.indoor.mapper.AccountMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountMapper.findByAccount(username);
        return account;
    }

    /*
     * アカウント情報追加
     */
    public void saveAccount(AccountForm reqAccount) {
        // パスワード暗号化処理
        String hashingPassword = passwordEncoder.encode(reqAccount.getPassword());
        reqAccount.setPassword(hashingPassword);

        Account saveAccount = setAccountEntity(reqAccount);
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

    /*
     * アカウント情報取得
     */
    public AccountForm findById(Integer id) {
        Account result = accountMapper.findById(id);
        AccountForm accountForm = setAccountForm(result);
        return accountForm;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private AccountForm setAccountForm(Account result) {
        AccountForm accountForm = new AccountForm();
        BeanUtils.copyProperties(result, accountForm);
        return  accountForm;
    }

    /*
     * アカウント情報更新
     */
    public void updateAccount(AccountForm reqAccount) {
        // パスワード暗号化処理
        String hashingPassword = passwordEncoder.encode(reqAccount.getPassword());
        reqAccount.setPassword(hashingPassword);

        Account updateAccount = setAccountEntity(reqAccount);
        accountMapper.update(updateAccount);
    }

    /*
     * アカウント削除
     */
    public void deleteAccount(Integer id) {
        accountMapper.deleteById(id);
    }

    public List<AccountForm> findAllAccount(String status, String account) {
        List<Account> results = accountMapper.findAllUser(status, account);
        List<AccountForm> accountForms = setForm(results);
        return accountForms;
    }

    private List<AccountForm> setForm(List<Account> results) {
        List<AccountForm> forms = new ArrayList<>();

        for (Account account : results) {
            AccountForm form = new AccountForm();
            BeanUtils.copyProperties(account, form);
            forms.add(form);
        }
        return forms;
    }

    public void stopAccount(int id) {
        accountMapper.stopAccount(id);
    }

    public void activeAccount(int id) {
        accountMapper.activeAccount(id);
    }
}