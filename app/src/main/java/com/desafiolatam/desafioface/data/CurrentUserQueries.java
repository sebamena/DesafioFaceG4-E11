package com.desafiolatam.desafioface.data;

import com.desafiolatam.desafioface.models.CurrentUser;

/**
 * Created by Sebastián Mena on 20-10-2017.
 */

public class CurrentUserQueries {

    public boolean isLogged(){
        return CurrentUser.listAll(CurrentUser.class).size() >0;
    }

    public CurrentUser get(){
        return CurrentUser.listAll(CurrentUser.class).get(0);
    }

}
