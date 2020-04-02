package com.github.metalloid.pagefactory.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ListUtils {

    public static Class<?> getListType(Field field) {
        // Type erasure in Java isn't complete. Attempt to discover the generic
        // type of the list.
        Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }

        Type listType = ((ParameterizedType) genericType).getActualTypeArguments()[0];

        return getClassFromParameterizedType(listType);
    }

    private static Class<?> getClassFromParameterizedType(Type type) {
        Class<?> clazz;
        try {
            clazz = Class.forName(type.getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return clazz;
    }
}
