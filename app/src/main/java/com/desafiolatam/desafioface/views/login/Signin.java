package com.desafiolatam.desafioface.views.login;

import com.desafiolatam.desafioface.models.CurrentUser;
import com.desafiolatam.desafioface.networks.LoginInteceptor;
import com.desafiolatam.desafioface.networks.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adacher on 20-06-17.
 */

public class Signin {

    private SessionCallback callback;

    public Signin(SessionCallback callback) {
        this.callback = callback;
    }


    public void toServer(String email, String password) {

        if (email.trim().length() <= 0 || password.trim().length() <= 0) {
            callback.requieredField();
        } else {
            if (!email.contains("@")) {
                callback.mailFormat();
            } else {
                Session session = new LoginInteceptor().get();
                Call<CurrentUser> call = session.loggin(email, password);
                call.enqueue(new Callback<CurrentUser>() {
                    @Override
                    public void onResponse(Call<CurrentUser> call, Response<CurrentUser> response) {
                        if (200 == response.code() && response.isSuccessful()) {
                            CurrentUser user = response.body();
                            user.create();
                            callback.success();
                        } else {
                            callback.failure();
                        }
                    }

                    @Override
                    public void onFailure(Call<CurrentUser> call, Throwable t) {
                        callback.failure();
                    }
                });

            }

        }
    }

}