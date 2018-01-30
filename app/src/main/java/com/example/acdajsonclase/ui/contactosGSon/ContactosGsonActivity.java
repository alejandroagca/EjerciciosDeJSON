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
import com.example.acdajsonclase.ui.contactos.Contacto;
import com.example.acdajsonclase.utils.Analisis;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ContactosGsonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String WEB = "https://portadaalta.mobi/acceso/contactos.json";
    private Button btnObtenerContactos;
    private ListView lwContactos;
    ArrayList<Contacto> contactos;
    ArrayAdapter<Contacto> adapter;
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
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                showError("ERROR: "+statusCode+ "\n" + throwable.getMessage());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    contactos = Analisis.analizarContactos(response);
                    mostrar();
                } catch (JSONException e) {
                    showError("ERROR: "+statusCode);
                }
                progreso.dismiss();

            }
        } );
    }

    private void mostrar() {
        if (contactos != null) {
            if (adapter == null) {
                adapter = new ArrayAdapter<Contacto>(this, android.R.layout.simple_list_item_1, contactos);
                lwContactos.setAdapter(adapter);
            } else {
                adapter.clear();
                adapter.addAll(contactos);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Toast.makeText(this, "Móvil: " + contactos.get(position).getTelefono().getMovil(), Toast.LENGTH_SHORT).show();
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
