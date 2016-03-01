package com.secretescapes.codingchallenge.db.hibernate;

import com.secretescapes.codingchallenge.db.AccountRepository;
import com.secretescapes.codingchallenge.domain.Account;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by Nigel on 2016-02-29.
 */
@Repository
public class AccountHibernateRepository extends GenericHibernateRepository<Account> implements AccountRepository {

    @Override
    public Account save(Account account) {
        if(account.getCreated() == null) {
            account.setCreated(new Date());
        }

        return super.save(account);
    }

    @Override
    public Account findById(Integer id) {
        Criteria criteria = getClassCriteria();
        criteria.add(Restrictions.eq("id", id));
        criteria.setFetchMode("transactions", FetchMode.JOIN);

        return (Account) criteria.uniqueResult();
    }

    public Account findByEmailAddress(String emailAddress) {
        Criteria criteria = getClassCriteria();
        criteria.add(Restrictions.eq("emailAddress", emailAddress));

        return (Account)criteria.uniqueResult();
    }

}
