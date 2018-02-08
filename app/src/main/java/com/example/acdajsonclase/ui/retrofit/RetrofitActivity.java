package com.example.acdajsonclase.ui.retrofit;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener, Callback<ArrayList<Repo>> {

    EditText edtUser;
    Button btnRepo;
    RecyclerView rvRepos;
    private ReposAdapter adapter;
    private ArrayList<Repo> repos;

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
        rvRepos.addOnItemTouchListener(new RecyclerTouchListener(this, rvRepos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                Uri uri = Uri.parse((String) repos.get(position).getHtmlUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), "No hay un navegador",
                            Toast.LENGTH_SHORT).show();
                Toast.makeText(RetrofitActivity.this, "Single Click on position:" + position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(RetrofitActivity.this, "Long press on position:" + position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }

    @Override
    public void onClick(View view) {
        if (view == btnRepo) {
            String username = edtUser.getText().toString();
            if (username.isEmpty()) {
                showMesage("Debes dar un nombre");
            } else {
                Call<ArrayList<Repo>> call = ApiAdapter.getInstance().listRepos(username);
                call.enqueue(this);

            }
        }

    }

    @Override
    public void onResponse(Call<ArrayList<Repo>> call, Response<ArrayList<Repo>> response) {
        if (response.isSuccessful()) {
            repos = response.body();
            adapter.setRepos(repos);
            showMesage("Repositorios actualizados correctamente");
        } else {
            StringBuilder message = new StringBuilder();
            message.append("Error en la descarga: " + response.code());
            if (response.body() != null) {
                message.append("\n" + response.body());
            }
            if (response.errorBody() != null) {
                message.append("\n" + response.errorBody());
            }
            showMesage(message.toString());
            }
    }

    @Override
    public void onFailure(Call<ArrayList<Repo>> call, Throwable t) {
        if (t != null) {
            showMesage("Fallo en la comunicacion: " + t.getMessage());
        }
    }

    public void showMesage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
