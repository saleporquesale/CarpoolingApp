package com.example.tab1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class agregarCategoria extends AppCompatActivity {
    private EditText nombreCategoriaET;
    private EditText minViajesET;
    private EditText minPuntosET;
    private EditText puntosTotalesET;
    private Button agregarCategoria;
    private String nombre;
    private String minViajes;
    private String minPuntos;
    private String puntosTotales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);
        nombreCategoriaET=findViewById(R.id.nombreCategoriaET);
        minPuntosET=findViewById(R.id.minPuntosET);
        minViajesET=findViewById(R.id.minViajesET);
        puntosTotalesET=findViewById(R.id.puntosTotalesET);
        agregarCategoria=findViewById(R.id.agregarCategoriaBtn);
        agregarCategoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                nombre=nombreCategoriaET.getText().toString();
                minViajes=minViajesET.getText().toString();
                minPuntos=minPuntosET.getText().toString();
                puntosTotales=puntosTotalesET.getText().toString();
                if(nombre.length()>0&&minViajes.length()>0&&minPuntos.length()>0&&puntosTotales.length()>0)
                {
                    RequestAsyncAgregarCategoria datosUsuario= (RequestAsyncAgregarCategoria)
                            new RequestAsyncAgregarCategoria().execute();
                    Intent irOpciones= new Intent(getApplicationContext(),OpcionesAdministrador.class);
                    startActivity(irOpciones);
                }
            }
        });
    }
    public class RequestAsyncAgregarCategoria extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("categoria", nombre);
                postDataParams.put("viajesmin", minViajes);
                postDataParams.put("puntosmin", minPuntos);
                postDataParams.put("puntostotales", puntosTotales);
                return RequestHandler.sendPost(Constante.url+"Administrador/Categoria/Agregar",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
}
