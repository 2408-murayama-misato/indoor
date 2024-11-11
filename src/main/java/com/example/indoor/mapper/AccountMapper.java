package com.example.indoor.mapper;


import com.example.indoor.entity.Account;
import com.example.indoor.service.AccountService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper
public interface AccountMapper {
    Account findByAccount(String account);
}
