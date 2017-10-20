package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.Developer;

import java.util.List;

/**
 * Created by Sebastián Mena on 20-10-2017.
 */

public class DeveloperQueries {

    public List<Developer> all(){
        return Developer.listAll(Developer.class);
    }

    public List<Developer> findByName(String name){

        String query = "name LIKE ?";
        String argument = "%"+name+"%";
        return Developer.find(Developer.class,query,argument);
    }

}
