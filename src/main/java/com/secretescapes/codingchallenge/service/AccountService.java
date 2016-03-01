package com.secretescapes.codingchallenge.service;

import com.secretescapes.codingchallenge.domain.Account;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Nigel on 2016-02-29.
 */
public interface AccountService {

    Account findById(Integer id);

    List<Account> findAll();

    Account transferFunds(Account fromAccount, Account toAccount, BigDecimal amount) throws InsufficientFundsException;

}
