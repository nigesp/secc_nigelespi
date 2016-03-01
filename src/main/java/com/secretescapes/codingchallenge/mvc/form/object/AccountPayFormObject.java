package com.secretescapes.codingchallenge.mvc.form.object;

import com.secretescapes.codingchallenge.domain.Account;

/**
 * Created by Nigel on 2016-02-29.
 */
public class AccountPayFormObject {

    private Account fromAccount;
    private Account toAccount;
    private String amount;

    public AccountPayFormObject() {}

    public AccountPayFormObject(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
