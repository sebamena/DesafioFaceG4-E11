package com.desafiolatam.desafioface.views.splash;

import com.desafiolatam.desafioface.models.CurrentUser;

import java.util.List;

/**
 * Created by adacher on 20-06-17.
 */

public class LoginValidation {


    private LoginCallback callback;

    public LoginValidation(LoginCallback loginCallback) {
        this.callback = loginCallback;
    }

    public void init() {
        List<CurrentUser> currentUsers = CurrentUser.listAll(CurrentUser.class);
        if (currentUsers != null && currentUsers.size() > 0) {
            callback.signed();

        } else {
            callback.signUp();
        }
    }
}
