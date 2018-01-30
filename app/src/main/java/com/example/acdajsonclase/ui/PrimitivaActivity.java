package com.example.acdajsonclase.ui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.acdajsonclase.R;
import com.example.acdajsonclase.network.MySingleton;
import com.example.acdajsonclase.network.RestClient;
import com.example.acdajsonclase.utils.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cz.msebera.android.httpclient.Header;

public class PrimitivaActivity extends AppCompatActivity implements View.OnClickListener {

    static String TAG = "TAG";
    static String WEB = "https://portadaalta.mobi/acceso/primitiva.json";
    private Button btnDescargar;
    private TextView txvTexto;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primitiva);
        btnDescargar = findViewById(R.id.btnDescargar);
        btnDescargar.setOnClickListener(this);
        txvTexto = findViewById(R.id.txvTexto);
        requestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
    }

    @Override
    public void onClick(View view) {
        if (view == btnDescargar) {
            descargar();
        }
    }

    public void descargar(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, WEB, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    try {
                        txvTexto.setText(Analisis.analizarPrimitiva(response));
                    } catch (JSONException e) {
                        txvTexto.setText(e.getMessage());
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                StringBuilder message = new StringBuilder();
                NetworkResponse response = error.networkResponse;

                if (response != null && response.data != null){
                    message.append("ERROR: "+ response.statusCode);
                } else {
                    String errorMessage = error.getClass().getSimpleName();
                    if (TextUtils.isEmpty(errorMessage)){
                        message.append("ERROR: "+ errorMessage);
                    } else {
                        message.append("Error de conexion con Volley");
                    }
                }
                showError(message.toString());
            }

        });

        jsonObjectRequest.setTag(TAG);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,1));
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Metodo para hacer un toast corto para que el codigo sea mas facil
     * @param message
     */
    public void toastShort(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    /**
     * Para mostrar errores hara un Toast y aparte mostrara el error en pantalla
     * @param message
     */
    public void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
