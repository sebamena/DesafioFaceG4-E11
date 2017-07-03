package com.desafiolatam.desafioface.networks;

import android.os.AsyncTask;
import android.util.Log;

import com.desafiolatam.desafioface.models.Developer;
import com.desafiolatam.desafioface.networks.users.UserInterceptor;
import com.desafiolatam.desafioface.networks.users.Users;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by adacher on 28-06-17.
 */

public class GetUsers extends AsyncTask<Map<String, String>, Integer, Integer> {

    private int additionalPages;
    private Map<String, String> queryMap;
    private int result;
    private final Users request = new UserInterceptor().get();

    public GetUsers(int additionalPages) {
        this.additionalPages = additionalPages;
    }

    @Override
    protected Integer doInBackground(Map<String, String>... params) {
        queryMap = params[0];
        if (additionalPages < 0) {
            while (200 == connect()) {
                increasePage();
            }
        } else {
            while (additionalPages >= 0) {
                additionalPages--;
                connect();
                increasePage();
            }

        }
        return null;
    }


    private void increasePage() {
        int page = Integer.parseInt(queryMap.get("page"));
        page++;
        queryMap.put("page", String.valueOf(page));
    }

    private int connect() {
        int code = 666;
        Call<Developer[]> call = request.get(queryMap);
        try {
            Response<Developer[]> response = call.execute();
            code = response.code();
            if (200 == code && response.isSuccessful()) {
                Developer[] developers = response.body();
                if (developers != null && developers.length > 0) {
                    Log.d("DEVELOPERS", String.valueOf(developers.length));
                    for (Developer serverDev : developers) {
                        List<Developer> localDevs = Developer.find(Developer.class, "serverId = ?", String.valueOf(serverDev.getId()));
                        if (localDevs != null && localDevs.size() > 0) {
                            Developer local = localDevs.get(0);
                            local.setEmail(serverDev.getEmail());
                            local.setPhoto_url(serverDev.getPhoto_url());
                            local.save();
                        } else {
                            serverDev.create();
                        }
                    }
                } else {
                    code = 777;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
            code = 888;
        }
        result = code;

        return result;
    }


}
