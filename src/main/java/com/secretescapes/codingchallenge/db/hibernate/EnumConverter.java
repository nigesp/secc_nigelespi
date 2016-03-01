package com.secretescapes.codingchallenge.db.hibernate;

/**
 * Created by Nigel on 2016-02-29.
 */
public interface EnumConverter<E extends Enum<E> & EnumConverter<E>> {
    byte convert();

    E convert(byte var1);
}
