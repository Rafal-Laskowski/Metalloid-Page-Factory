package com.github.metalloid.pagefactory.utils;

import com.github.metalloid.logging.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ListUtils {
    private static Logger logger = new Logger(ListUtils.class);

    public static Class<?> getListType(Field field) {
        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        Class<?> clazz;
        try {
            clazz = Class.forName(listType.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }
}
