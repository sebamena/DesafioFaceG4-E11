package com.desafiolatam.desafioface.networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adacher on 20-06-17.
 */

public class LoginInteceptor {

    public static final String BASE_URL = "https://empieza.desafiolatam.com/";


    public Session get() {

        Retrofit interceptor = new Retrofit.Builder()
                .baseUrl(BASE_URL)
            /*Never forget about adding the converter, otherwise you can not parse the data*/
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Session session = interceptor.create(Session.class);
        return session;

    }

}
