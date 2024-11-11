package com.example.indoor.mapper;


import com.example.indoor.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {
    Account findByAccount(String account);
    //レコード追加
    void save(Account account);
}
