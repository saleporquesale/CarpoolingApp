package com.example.tab1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AgregarAuto extends AppCompatActivity
{
    private EditText placaET;
    private EditText marcaET;
    private EditText modeloET;
    private EditText numeroPasajerosET;
    private Button guardarAutoBtn;
    private String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);
        placaET=findViewById(R.id.placaET);
        marcaET=findViewById(R.id.marcaET);
        modeloET=findViewById(R.id.modeloET);
        numeroPasajerosET=findViewById(R.id.numeroPasajerosET);
        guardarAutoBtn=findViewById(R.id.guardarAutoBtn);
        idUser=getIntent().getStringExtra("idKey");
        //Guardar auto
        guardarAutoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Obteniendo datos de ET
                String placa=placaET.getText().toString();
                String marca=marcaET.getText().toString();
                String modelo=modeloET.getText().toString();
                int numeroPasajeros=Integer.parseInt(numeroPasajerosET.getText().toString());
                //Verificando datos
                if(verificarCampos(placa,marca,modelo,numeroPasajeros))
                {
                    //Enviando datos
                    RequestAsync post= (RequestAsync) new RequestAsync().execute();
                    String resultadoPost= null;
                    //Obteniendo respuesta
                    try {
                        resultadoPost = post.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(resultadoPost.contains("OK"))
                    {
                        Intent irMenu= new Intent(getApplicationContext(),MenuBottom.class);
                        irMenu.putExtra("idKey",idUser);
                        startActivity(irMenu);
                    }
                }
            }
        });
    }
    //Verificadores de campos
    private boolean verificarCampos(String placa,String marca,String modelo,int numeroPasajeros)
    {
        boolean resultado=revisarCampo(placa,20,"Ingrese la placa de su vehículo");
        resultado=resultado&&revisarCampo(marca,45,"Ingrese la marca de su vehículo");
        resultado=resultado&&revisarCampo(modelo,45,"Ingrese la modelo de su vehículo");
        resultado=resultado&&reviasarNumeroPasajeros(numeroPasajeros);
        return resultado;
    }
    private boolean revisarCampo (String campo,int maximo,String hint)
    {
        boolean resultado;
        if (campo.length() > 0)
        {
            if (campo.length() <= maximo)
            {
                resultado = true;
            }
            else
            {
                resultado = false;
            }

        }
        else
        {
            placaET.setText(hint);
            resultado = false;
        }
        return resultado;
    }
    private boolean reviasarNumeroPasajeros (int numeroPasajeros)
    {
        boolean resultado;
        if(numeroPasajeros>=2)
        {
            resultado=true;
        }
        else
        {
            numeroPasajerosET.setHint("Ingrese la cantidad de pasajeros");
            resultado=false;
        }
        return resultado;
    }
    public class RequestAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", idUser);
                postDataParams.put("placa", placaET.getText().toString());
                postDataParams.put("marca", marcaET.getText().toString());
                postDataParams.put("modelo", modeloET.getText().toString());
                postDataParams.put("numero", numeroPasajerosET.getText().toString());
                return RequestHandler.sendPost(Constante.url+"Aun No Hay",postDataParams);
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
