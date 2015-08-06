package com.prefabsoft.service;

import com.google.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Produces;

/**
 * Created by jochen on 05/08/15.
 */
public class LoggerProducer {
    /**
     * @param injectionPoint
     * @return logger
     */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}