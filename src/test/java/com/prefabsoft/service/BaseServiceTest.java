package com.prefabsoft.service;

import com.prefabsoft.config.ConfigurationFactory;
import com.prefabsoft.config.FieldSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by jochen on 06/08/15.
 */
public class BaseServiceTest {

    public final static Logger logger = LoggerFactory.getLogger(BaseServiceTest.class.getName());

    public void processConfigAnnotations(Object object){
        ConfigurationFactory.processAnnotations(object, new FieldSetter(){
            public void set(Field field, Object object, Object value) throws IllegalAccessException {
                field.set(object, value);
            }
        });
        /*ConfigurationFactory.processAnnotations(object, new FieldSetter(){
            public void set(Field field, Object object, Object value) throws IllegalAccessException {
                field.set(object, value);
            }
        });*/
    }


}
