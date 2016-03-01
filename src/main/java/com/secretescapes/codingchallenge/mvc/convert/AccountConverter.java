package com.secretescapes.codingchallenge.mvc.convert;

import com.secretescapes.codingchallenge.domain.Account;
import com.secretescapes.codingchallenge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Nigel on 2016-03-01.
 */
public class AccountConverter implements Converter<String, Account> {

    @Autowired
    AccountService accountService;

    @Override
    public Account convert(String accountId) {
        try{
            int id = Integer.parseInt(accountId);
            if(id < 0 ) {
                return null;
            }
            return accountService.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
