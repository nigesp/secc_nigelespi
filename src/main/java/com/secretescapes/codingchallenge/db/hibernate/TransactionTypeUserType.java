package com.secretescapes.codingchallenge.db.hibernate;

import com.secretescapes.codingchallenge.domain.TransactionType;

/**
 * Created by Nigel on 2016-02-29.
 */
public class TransactionTypeUserType extends PersistentEnumUserType<TransactionType> {

    @Override
    public Class<TransactionType> returnedClass() {
        return TransactionType.class;
    }

}
