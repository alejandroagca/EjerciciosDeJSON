package com.example.acdajsonclase.ui.retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.acdajsonclase.R;
import com.example.acdajsonclase.network.ApiAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUser;
    Button btnRepo;
    RecyclerView rvRepos;

    private ReposAdapter adapter;
    private ArrayList<Repo> repos;
    private String WEB;
    private String USER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        edtUser = (EditText) findViewById(R.id.edtUser);
        btnRepo = (Button) findViewById(R.id.btnRepo);
        btnRepo.setOnClickListener(this);
        rvRepos = (RecyclerView) findViewById(R.id.rvRepo);
        adapter = new ReposAdapter();
        rvRepos.setAdapter(adapter);
        rvRepos.setLayoutManager(new LinearLayoutManager(this));

        rvRepos.addOnItemTouchListener(new RecyclerTouchListener(this,rvRepos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Uri uri = Uri.parse((String) repos.get(position).getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), "No hay un navegador",
                            Toast.LENGTH_SHORT).show();
                Toast.makeText(RetrofitActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(RetrofitActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view == btnRepo) {
            USER = edtUser.getText().toString();
            WEB = "https://api.github.com/users/"+USER+"/repos";

            Call<List<Repo>> call = ApiAdapter.getApiService().reposForUser(USER);

            call.enqueue(new Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                    // The network call was a success and we got a response
                    // TODO: use the repository list and display it
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
                    // the network call was a failure
                    // TODO: handle error
                }
            });
        }
    }


    /**
     * Para mostrar errores hara un Toast y aparte mostrara el error en pantalla
     *
     * @param message
     */
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
