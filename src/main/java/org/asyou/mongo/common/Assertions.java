package org.asyou.mongo.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by steven on 17/9/21.
 */
public final class Assertions {
    public static <T> T notNull(String name, T value) {
        if(value == null) {
            throw new IllegalArgumentException(name + " can not be null");
        } else {
            return value;
        }
    }

    public static boolean allNull(String message, String... values){
        for(String str : values){
            if (StringUtils.isNotBlank(str)) {
                return false;
            }
        }
        throw new IllegalArgumentException(message + " values is all null");
    }

    public static void mustBeTrue(String name, boolean condition) {
        if(!condition) {
            throw new IllegalStateException("state should be: " + name);
        }
    }

    public static String mustBeLength(String value, int length){
        if (value.length() < length) {
            throw new IllegalArgumentException(String.format("value='%s',length='%s' ; length must be '%s'",value,value.length(),length));
        }
        if (value.length() > length) {
            value = value.substring(0, length);
        }
        return value;
    }

    public static int zeroOrOne(int value){
        return value == 0 ? 0 : 1;
    }
}
