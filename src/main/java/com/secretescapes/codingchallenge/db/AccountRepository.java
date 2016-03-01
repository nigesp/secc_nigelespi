package com.secretescapes.codingchallenge.db;

import com.secretescapes.codingchallenge.db.GenericRepository;
import com.secretescapes.codingchallenge.domain.Account;

/**
 * Created by Nigel on 2016-02-29.
 */
public interface AccountRepository extends GenericRepository<Account> {

    Account findByEmailAddress(String emailAddress);

}
