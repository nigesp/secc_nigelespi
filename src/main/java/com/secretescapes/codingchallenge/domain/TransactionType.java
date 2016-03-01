package com.secretescapes.codingchallenge.domain;

import com.secretescapes.codingchallenge.db.hibernate.EnumConverter;
import com.secretescapes.codingchallenge.util.ReverseEnumMap;

/**
 * Created by Nigel on 2016-02-29.
 */
public enum TransactionType implements EnumConverter<TransactionType> {

    DEBIT(1, "Debit account"),
    CREDIT(2, "Credit account"),
    BALANCE(3, "Balance request");

    private final byte val;
    private String description;

    private static ReverseEnumMap<TransactionType> map = new ReverseEnumMap<>(TransactionType.class);

    TransactionType(int val, String description) {
        this.val = (byte) val;
        this.description = description;
    }

    @Override
    public byte convert() {
        return val;
    }

    @Override
    public TransactionType convert(byte b) {
        TransactionType type = map.get(b);
        return type;
    }

}
