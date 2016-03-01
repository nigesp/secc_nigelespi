package com.secretescapes.codingchallenge.service;

import com.secretescapes.codingchallenge.db.AccountRepository;
import com.secretescapes.codingchallenge.domain.Account;
import com.secretescapes.codingchallenge.service.impl.AccountServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Nigel on 2016-02-29.
 */
public class UnitAccountServiceTest {

    AccountRepository mockAccountRepository;
    SessionFactory mockSessionFactory;
    Session mockSession;

    Account fromAccount;
    Account toAccount;

    AccountService accountService = new AccountServiceImpl();

    @Before
    public void init() {
        //Mock out AccountRepository.
        mockAccountRepository = mock(AccountRepository.class);
        when(mockAccountRepository.save(Matchers.any(Account.class))).thenReturn(new Account());

        //Mock out session method calls.
        mockSession = mock(Session.class);
        when(mockSession.merge(Matchers.any(Account.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return args[0];
            }
        });

        //Mock out SessionFactory method calls.
        mockSessionFactory = mock(SessionFactory.class);
        when(mockSessionFactory.getCurrentSession()).thenReturn(mockSession);

        //Setup accounts.
        fromAccount = new Account();
        fromAccount.setBalance(new BigDecimal("200.00"));
        fromAccount.setCreated(new Date());
        fromAccount.setName("Test User");
        fromAccount.setEmailAddress("test.user@tester.com");

        toAccount = new Account();
        toAccount.setBalance(new BigDecimal("200.00"));
        toAccount.setCreated(new Date());
        toAccount.setName("Test User 2");
        toAccount.setEmailAddress("test.user.2@tester.com");

        ReflectionTestUtils.setField(accountService, "accountRepository", mockAccountRepository);
        ReflectionTestUtils.setField(accountService, "sessionFactory", mockSessionFactory);
    }

    @Test
    public void transferFundsAndExpectSuccess() {
        try{
            accountService.transferFunds(fromAccount, toAccount, new BigDecimal("50.00"));
        } catch (InsufficientFundsException e) {}

        assertEquals("The balance of the 'from account' should now be 150.00", new BigDecimal("150.00"), fromAccount.getBalance());
        assertEquals("The balance of the 'to account' should now be 250.00", new BigDecimal("250.00"), toAccount.getBalance());
    }

    @Test(expected = InsufficientFundsException.class)
    public void transferFundsAndExpectInsufficientFundsException() throws InsufficientFundsException {
        accountService.transferFunds(fromAccount, toAccount, new BigDecimal("550.00"));
    }

}
