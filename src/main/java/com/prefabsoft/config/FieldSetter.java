package com.prefabsoft.config;

import java.lang.reflect.Field;

/**
 * Created by jochen on 06/08/15.
 */
public interface FieldSetter {

    void set(Field field, Object object, Object value) throws IllegalAccessException;

}
