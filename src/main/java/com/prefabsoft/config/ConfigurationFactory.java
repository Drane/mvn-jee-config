package com.prefabsoft.config;

import com.google.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by jochen on 05/08/15.
 */
//@Singleton
public class ConfigurationFactory {

    final static Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class.getName());

    private volatile static Properties configProperties;
    public static final String propertiesFilePath = "/config/application.properties";

    public synchronized static Properties getProperties() {

        if (configProperties == null) {
            configProperties = new Properties();
            try {
                configProperties.load(ConfigurationFactory.class.getResourceAsStream(propertiesFilePath));
            } catch (IOException ex) {
                logger.error("IOException: {}", ex);
                throw new RuntimeException(ex);
            }

        }

        return configProperties;
    }

    @Produces
    @Config
    public String getConfiguration(InjectionPoint p) {
        Member member = p.getMember();
        String configKey = getConfigKeyFromMember(member);
        return getProperties().getProperty(configKey);
    }

    private static String getConfigKeyFromMember(Member member) {
        String configKey = member.getDeclaringClass().getName() + "." + member.getName();
        Properties config = getProperties();
        if (config.getProperty(configKey) == null) {
            configKey = member.getDeclaringClass().getSimpleName() + "." + member.getName();
            if (config.getProperty(configKey) == null)
                configKey = member.getName();
        }
        logger.error("Config key: {} value = {}", configKey, config.getProperty(configKey));
        return configKey;
    }

    @Produces
    @Config
    public Double getConfigurationDouble(InjectionPoint p) {
        String val = getConfiguration(p);
        return Double.parseDouble(val);

    }

    public static void processAnnotations(Object object) {
        processAnnotations(object, null);
    }

    public static void processAnnotations(Object object, FieldSetter fieldSetter) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Config.class)) {
                Config fAnno = field.getAnnotation(Config.class);
                logger.debug("Field: {} Config: {}", field.getName(), fAnno);
                String configKey = getConfigKeyFromMember(field);
                logger.debug("configKey:" + configKey);
                try {
                    if(null != fieldSetter && ! Modifier.isPrivate(field.getModifiers())){
                        fieldSetter.set(field, object, getProperties().getProperty(configKey));
                    }else if(Modifier.isPublic(field.getModifiers())){
                        field.set(object, getProperties().getProperty(configKey));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*public static List<FieldSetter2> processAnnotations2(Object object) {
        List<FieldSetter2> result = new ArrayList<FieldSetter2>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Config.class)) {
                Config fAnno = field.getAnnotation(Config.class);
                logger.debug("Field: {} Config: {}", field.getName(), fAnno);
                String configKey = getConfigKeyFromMember(field);
                logger.debug("configKey:" + configKey);
                result.add(new FieldSetterImpl(field, object, getProperties().getProperty(configKey)));
            }
        }
        return result;
    }*/
    /*public static SetMethod processAnnotations2(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Config.class)) {
                Config fAnno = field.getAnnotation(Config.class);
                logger.debug("Field: {} Config: {}", field.getName(), fAnno);
                String configKey = getConfigKeyFromMember(field);
                logger.debug("configKey:" + configKey);
                return new SetMethod(field, object, getProperties().getProperty(configKey));
            }

        }
        return null;
    }


    public static class SetMethod {
        private final Field field;
        private final Object object;
        private final Object value;

        public SetMethod(Field field, Object object, Object value) {
            this.field = field;
            this.object = object;
            this.value = value;
        }

        public Method invoke() {
            Method set = null;
            try {
                set = field.getClass().getMethod("set", Object.class, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            return set;
           *//* try {
                set.invoke(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }*//*
        }
    }*/
}