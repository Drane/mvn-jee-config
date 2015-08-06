package com.prefabsoft.service;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jochen on 06/08/15.
 */
@Stateless
public class FakeDao {

    public List<String> findAll(){
        return Arrays.asList("A", "B", "C");
    }

}
