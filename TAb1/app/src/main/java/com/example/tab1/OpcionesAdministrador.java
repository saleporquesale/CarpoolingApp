package com.example.tab1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class OpcionesAdministrador extends AppCompatActivity
{
    private ArrayList<String> correosReporteAL=new ArrayList<>();
    private ArrayList<String> categoriasAL=new ArrayList<>();
    private ArrayList<String> categoriasNombreAL=new ArrayList<>();
    private ListView correosLV;
    private ListView categoriasLV;
    private ImageButton agregarCorreo;
    private ImageButton agregarCategoria;
    private EditText correoNuevoET;
    private String categoriaPorEliminar="";
    private String correoPorEliminar="";
    private String correoNuevo="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_administrador);
        correosLV =findViewById(R.id.correosReportesLV);
        categoriasLV=findViewById(R.id.categoriasLV);
        agregarCategoria=findViewById(R.id.agregarCategoriasBtn);
        agregarCorreo=findViewById(R.id.agregarCorreoReporteBtn);
        correoNuevoET=findViewById(R.id.nuevoCorreoET);
        //Obteniendo datos
        getCorreosReporteAL();
        getCategoriasAL();
        //Configuracion del LV Correos
        final ArrayAdapter adapterCorreos=new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1, correosReporteAL);
        correosLV.setAdapter(adapterCorreos);
        // Logica LV Correos
        correosLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                correoPorEliminar=correosReporteAL.get(position);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(OpcionesAdministrador.this);
                dialogo1.setTitle("Eliminar correo");
                dialogo1.setMessage("¿ Desea eliminar este correo ?");
                dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialogo1, int id)
                    {
                        //Enviando la categoria a eliminar
                        RequestAsyncEliminarCorreos datosUsuario= (RequestAsyncEliminarCorreos)
                                new RequestAsyncEliminarCorreos().execute();
                        getCorreosReporteAL();
                        correosLV.setAdapter(adapterCorreos);
                    }
                });
                dialogo1.setNegativeButton("Negar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(getApplicationContext(),"bueno",Toast.LENGTH_LONG).show();
                    }
                });
                dialogo1.show();
                //Actualizando luego de borrar la categoria
                getCorreosReporteAL();
                correosLV.setAdapter(adapterCorreos);
            }
        });
        //Configuracion del LV Categorias
        final ArrayAdapter adapterCategorias=new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_list_item_1, categoriasAL);
        categoriasLV.setAdapter(adapterCategorias);
        // Logica LV Categorias
        categoriasLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
//                categoriaPorEliminar=categoriasNombreAL.get(position);
//                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(OpcionesAdministrador.this);
//                dialogo1.setTitle("Eliminar Categoria");
//                dialogo1.setMessage("¿ Desea eliminar esta categoria ?");
//                dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialogo1, int id)
//                    {
//                        //Enviando la categoria a eliminar
//                        RequestAsyncEliminarCategoria datosUsuario= (RequestAsyncEliminarCategoria)
//                                new RequestAsyncEliminarCategoria().execute();
//                        JSONParser parser = new JSONParser();
//                        JSONArray resultadoPost=new JSONArray();
//                        getCategoriasAL();
//                        categoriasLV.setAdapter(adapterCategorias);
//                    }
//                });
//                dialogo1.setNegativeButton("Negar", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialogo1, int id) {
//                        Toast.makeText(getApplicationContext(),"bueno",Toast.LENGTH_LONG).show();
//                    }
//                });
//                dialogo1.show();
//                //Actualizando luego de borrar la categoria
//                getCategoriasAL();
//                categoriasLV.setAdapter(adapterCategorias);
            }
        });
        //Boton Agregar Correo
        agregarCorreo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(correoNuevoET.getText().toString().length()>0)
                {
                    correoNuevo=correoNuevoET.getText().toString();
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(OpcionesAdministrador.this);
                    dialogo1.setTitle("Agregar correo");
                    dialogo1.setMessage("¿ Desea agregar este correo ?");
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            //Enviando la categoria a eliminar
                            RequestAsyncNuevoCorreo datosUsuario= (RequestAsyncNuevoCorreo)
                                    new RequestAsyncNuevoCorreo().execute();
                            JSONParser parser = new JSONParser();
                            JSONArray resultadoPost=new JSONArray();
                            getCorreosReporteAL();
                            correosLV.setAdapter(adapterCorreos);

                        }
                    });
                    dialogo1.setNegativeButton("Negar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            Toast.makeText(getApplicationContext(),"bueno",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogo1.show();
                }
            }
        });
        //Boton Agregar Categoria
        agregarCategoria.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent irAgregarCategoria= new Intent(getApplicationContext(),agregarCategoria.class);
                startActivity(irAgregarCategoria);
            }
        });
    }
    private void getCorreosReporteAL()
    {
        correosReporteAL.clear();
        RequestAsyncCorreos datosUsuario= (RequestAsyncCorreos)
                new RequestAsyncCorreos().execute();
        JSONParser parser = new JSONParser();
        JSONArray resultadoPost=new JSONArray();
        try
        {
            resultadoPost = (JSONArray) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<resultadoPost.size();i++)
        {
            org.json.simple.JSONObject correoDato= (org.json.simple.JSONObject) resultadoPost.get(i);
            String correo="";
            correo= (String) correoDato.get("correo");
            Toast.makeText(getApplicationContext(),"Correo recibido"+i+" correo",Toast.LENGTH_LONG).show();
            correosReporteAL.add(correo);
        }
    }
    private void getCategoriasAL()
    {
        categoriasNombreAL.clear();
        categoriasAL.clear();
        RequestAsyncCategorias datosUsuario= (RequestAsyncCategorias)
                new RequestAsyncCategorias().execute();
        JSONParser parser = new JSONParser();
        JSONArray resultadoPost=new JSONArray();
        try
        {
            resultadoPost = (JSONArray) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<resultadoPost.size();i++)
        {
            org.json.simple.JSONObject categoriaDatos= (org.json.simple.JSONObject) resultadoPost.get(i);
            String categoria="";
            long minPts= (long) categoriaDatos.get("minpuntos");
            long viajesMin= (long) categoriaDatos.get("viajesmin");
            categoria= (String) categoriaDatos.get("categoria");
            categoriasAL.add("Categoria: "+categoria+"\nPuntos minimos: "+minPts
                +"\nViajes minimos: "+viajesMin);
            categoriasNombreAL.add(categoria);
        }
    }
    public class RequestAsyncCorreos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Administrador/Correo",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class RequestAsyncNuevoCorreo extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("correo", correoNuevo);
                return RequestHandler.sendPost(Constante.url+"Administrador/Correo/Agregar",postDataParams);
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
    public class RequestAsyncCategorias extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Administrador/Categorias",postDataParams);
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
    public class RequestAsyncEliminarCategoria extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("categoria", categoriaPorEliminar);
                return RequestHandler.sendPost(Constante.url+"Administrador/Categoria/Eliminar",postDataParams);
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
    public class RequestAsyncEliminarCorreos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("correo", correoPorEliminar);
                return RequestHandler.sendPost(Constante.url+"Administrador/Correo/Eliminar",postDataParams);
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
