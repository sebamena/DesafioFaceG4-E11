package com.desafiolatam.desafioface.views.splash;

import com.desafiolatam.desafioface.data.CurrentUserQueries;

/**
 * Created by adacher on 20-06-17.
 */

public class LoginValidation {

    private LoginCallback callback;

    public LoginValidation(LoginCallback loginCallback) {
        this.callback = loginCallback;
    }

    public void init() {
        if (new CurrentUserQueries().isLogged()) {
            callback.signed();

        } else {
            callback.signUp();
        }
    }
}
