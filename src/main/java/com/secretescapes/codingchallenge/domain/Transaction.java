package com.secretescapes.codingchallenge.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Nigel on 2016-02-29.
 */
@Entity
public class Transaction implements Comparable<Transaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Date created;

    @Type(type = "com.secretescapes.codingchallenge.db.hibernate.TransactionTypeUserType")
    private TransactionType type;

    private BigDecimal amount;

    @OneToOne
    private Account otherAccount;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(Account otherAccount) {
        this.otherAccount = otherAccount;
    }

    @Override
    public int compareTo(Transaction o) {
        return o.getCreated().compareTo(this.created);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Transaction) {
            if(this == o) {
                return true;
            }
            Transaction t = (Transaction) o;
            if(this.amount.equals(t.getAmount()) && this.account.equals(t.getAccount()) && this.getType() == t.getType()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (amount.hashCode() + account.hashCode() + type.hashCode()) * 7;
    }
}
