package com.example.indoor.mapper;


import com.example.indoor.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;

@Mapper
public interface AccountMapper {
    Account findByAccount(String account);
    //アカウント情報追加
    void save(Account account);
    //アカウント情報取得
    Account findById(Integer id);
    //アカウント情報更新
    void update(Account account);
    //アカウント削除
    void deleteById(Integer id);
}
