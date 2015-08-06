package com.prefabsoft.service;

import com.prefabsoft.config.Config;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by jochen on 05/08/15.
 */
@Path("/")
//@Stateless
public class FakeService {

    private final FakeDao fakeDao;

    @Inject
    Logger logger;

    @Inject @Config
    String serverAddress;


    public FakeService(FakeDao fakeDao) {
        this.fakeDao = fakeDao;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRoot(){
        logger.warn("Dit is een test: {}", 1);
        return "serverAddress:" + serverAddress + " fakeDaoSTuff: " + fakeDao.findAll();
    }

}
