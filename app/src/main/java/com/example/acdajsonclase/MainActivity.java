package com.example.acdajsonclase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.acdajsonclase.ui.contactos.ContactosActivity;
import com.example.acdajsonclase.ui.contactosGSon.ContactosGsonActivity;
import com.example.acdajsonclase.ui.CreacionJsonActivity;
import com.example.acdajsonclase.ui.PrimitivaActivity;
import com.example.acdajsonclase.ui.RetrofitActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPrimitiva, btnContactos, btnContactosGson, btnCreacionJson, btnRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPrimitiva = findViewById(R.id.btnPrimitiva);
        btnPrimitiva.setOnClickListener(this);
        btnContactos = findViewById(R.id.btnContactos);
        btnContactos.setOnClickListener(this);
        btnContactosGson = findViewById(R.id.btnContactosGson);
        btnContactosGson.setOnClickListener(this);
        btnCreacionJson = findViewById(R.id.btnCreacionJson);
        btnCreacionJson.setOnClickListener(this);
        btnRetrofit = findViewById(R.id.btnRetrofit);
        btnRetrofit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnPrimitiva) {
            startActivity(new Intent(MainActivity.this, PrimitivaActivity.class));
        }
        if (view == btnContactos) {
            startActivity(new Intent(MainActivity.this, ContactosActivity.class));
        }
        if (view == btnContactosGson) {
            startActivity(new Intent(MainActivity.this, ContactosGsonActivity.class));
        }
        if (view == btnCreacionJson) {
            startActivity(new Intent(MainActivity.this, CreacionJsonActivity.class));
        }
        if (view == btnRetrofit) {
            startActivity(new Intent(MainActivity.this, RetrofitActivity.class));
        }
    }
}
