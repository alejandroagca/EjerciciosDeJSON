package com.example.acdajsonclase.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by usuario on 6/02/18.
 */

public interface ApiService {
    @GET("/users/{user}/repos")
    Call<List<Repo>> reposForUser(@Path("user") String user);
}
