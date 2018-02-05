package com.example.acdajsonclase.ui.contactosGSon;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.acdajsonclase.R;
import com.example.acdajsonclase.network.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContactosGsonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String WEB = "https://portadaalta.mobi/acceso/contacts.json";
    private Button btnObtenerContactos;
    private ListView lwContactos;
    ArrayAdapter<Contact> adapter;
    Gson gson;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);
        btnObtenerContactos = findViewById(R.id.btnObtenerContactos);
        btnObtenerContactos.setOnClickListener(this);
        lwContactos = findViewById(R.id.lwContactos);
        lwContactos.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnObtenerContactos) {
            descarga(WEB);
        }
    }

    private void descarga(String web) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RestClient.get(web, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(true);
                progreso.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString,Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progreso.dismiss();
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Fallo en la descarga (string)");
                if (throwable != null) {
                    mensaje.append(": " + statusCode + "\n" + throwable.getMessage());
                    if (responseString != null) {
                        mensaje.append("\n" + responseString);
                    }
                }
                showError(mensaje.toString());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progreso.dismiss();
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Fallo en la descarga (JSONObject)");
                if (throwable != null) {
                    mensaje.append(": " + statusCode + "\n" + throwable.getMessage());
                    if (errorResponse != null) {
                        mensaje.append("\n" + errorResponse.toString());
                    }
                }
                showError(mensaje.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    gson = new Gson();
                    person = gson.fromJson(response.toString(), Person.class);
                    mostrar();
                }
                catch (JsonSyntaxException e){
                    showError("Error en GSON: "+ e.getMessage());
                }
                progreso.dismiss();

            }
        } );
    }

    private void mostrar() {
        if (person != null) {
            if (adapter == null) {
                adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_list_item_1, person.getContacts());
                lwContactos.setAdapter(adapter);
            }
            else {
                adapter.clear();
                adapter.addAll(person.getContacts());
            }
        } else
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "Móvil: " + person.getContacts().get(position).getPhone().getMobile(), Toast.LENGTH_SHORT).show();
    }

    public void showError(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
