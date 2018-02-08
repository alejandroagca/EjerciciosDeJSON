package com.example.acdajsonclase.network;

import com.example.acdajsonclase.ui.retrofit.Repo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by usuario on 6/02/18.
 */

public interface ApiService {

    @GET("users/{username}/repos")
    Call<ArrayList<Repo>> listRepos(@Path("username") String username);

}
