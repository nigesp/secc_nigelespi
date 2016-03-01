package com.secretescapes.codingchallenge.service.impl;

import com.secretescapes.codingchallenge.db.AccountRepository;
import com.secretescapes.codingchallenge.domain.Account;
import com.secretescapes.codingchallenge.domain.Transaction;
import com.secretescapes.codingchallenge.domain.TransactionType;
import com.secretescapes.codingchallenge.service.AccountService;
import com.secretescapes.codingchallenge.service.InsufficientFundsException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Nigel on 2016-02-29.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class AccountServiceImpl implements AccountService {

    private static Logger log = Logger.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    SessionFactory sessionFactory;

    public Account findById(Integer id) {
        return accountRepository.findById(id);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Account transferFunds(Account fromAccount, Account toAccount, BigDecimal amount) throws InsufficientFundsException {
        //Merge account params into hibernate session.
        fromAccount = (Account) sessionFactory.getCurrentSession().merge(fromAccount);
        toAccount = (Account) sessionFactory.getCurrentSession().merge(toAccount);

        if(fromAccount.getBalance().compareTo(amount) < 0) {
            log.error("ERROR - Transfer from account: " + fromAccount.getId() + " to account: " + toAccount.getId() + " failed due to INSUFFICIENT funds");
            throw new InsufficientFundsException();
        }

        //Add debit transaction to from Account.
        Transaction txDebit = new Transaction();
        txDebit.setCreated(new Date());
        txDebit.setAmount(amount);
        txDebit.setOtherAccount(toAccount);
        txDebit.setType(TransactionType.DEBIT);

        fromAccount.addTransaction(txDebit);

        //Add credit transaction to toAccount.
        Transaction txCredit = new Transaction();
        txCredit.setCreated(new Date());
        txCredit.setAmount(amount);
        txCredit.setOtherAccount(fromAccount);
        txCredit.setType(TransactionType.CREDIT);

        toAccount.addTransaction(txCredit);

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return fromAccount;
    }

}
