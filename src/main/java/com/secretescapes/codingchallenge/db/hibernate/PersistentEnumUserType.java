package com.secretescapes.codingchallenge.db.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nigel on 2016-02-29.
 */
public abstract class PersistentEnumUserType<E extends EnumConverter<?>> implements UserType {
    private static final int[] SQL_TYPES = new int[]{-6};

    public PersistentEnumUserType() {
    }

    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    public abstract Class<E> returnedClass();

    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y?true:(null != x && null != y?x.equals(y):false);
    }

    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        byte val = rs.getByte(names[0]);
        if(rs.wasNull()) {
            return null;
        } else {
            Enum value = ((EnumConverter[])this.returnedClass().getEnumConstants())[0].convert(val);
            if(value == null) {
                throw new IllegalStateException("Unknown " + this.returnedClass().getSimpleName() + " value");
            } else {
                return value;
            }
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if(value == null) {
            st.setNull(index, -6);
        } else {
            st.setInt(index, ((EnumConverter)value).convert());
        }

    }

    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }

    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }
}
