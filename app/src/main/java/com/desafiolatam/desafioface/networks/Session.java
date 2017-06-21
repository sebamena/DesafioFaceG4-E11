package com.desafiolatam.desafioface.networks;

import com.desafiolatam.desafioface.models.CurrentUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by adacher on 20-06-17.
 */

public interface Session {

    @FormUrlEncoded
    @POST("mobile_sessions")
    Call<CurrentUser> loggin (@Field("session[email]")String email, @Field("session[password]") String password);

}
