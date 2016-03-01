package com.secretescapes.codingchallenge.util;

import com.secretescapes.codingchallenge.db.hibernate.EnumConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nigel on 2016-02-29.
 */
public class ReverseEnumMap<V extends Enum<V> & EnumConverter<V>> {
    private Map<Byte, V> map = new HashMap<Byte, V>();

    /**
     * Construct a <code>ReverseEnumMap</code> from the specified
     * <code>Class</code>
     *
     * @param valueType
     *            the type for which to generate the reverse map
     */
    public ReverseEnumMap(Class<V> valueType) {
        for (V v : valueType.getEnumConstants()) {
            map.put(v.convert(), v);
        }
    }

    /**
     * Get a enum from a byte
     *
     * @param num the byte to look up
     * @return the corresponding enum
     */
    public V get(byte num) {
        return map.get(num);
    }
}