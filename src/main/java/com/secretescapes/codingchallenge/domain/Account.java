package com.secretescapes.codingchallenge.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

/**
 * Created by Nigel on 2016-02-29.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"emailAddress", "name"})})
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private BigDecimal balance;
    private String emailAddress;
    private Date created;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Sort(type = SortType.NATURAL)
    private SortedSet<Transaction> transactions = new TreeSet<>();

    public SortedSet<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(SortedSet<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void addTransaction(Transaction tx) {
        tx.setAccount(this);
        transactions.add(tx);
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Account) {
            if(this == o) {
                return true;
            }
            Account a = (Account) o;
            if(this.emailAddress.equals(a.getEmailAddress()) && this.balance.equals(a.getBalance()) && this.name.equals(a.getName())) {
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
        return (emailAddress.hashCode() + balance.hashCode() + name.hashCode()) * 7;
    }
}
