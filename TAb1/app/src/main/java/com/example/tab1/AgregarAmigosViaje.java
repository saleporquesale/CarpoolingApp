package com.example.tab1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AgregarAmigosViaje extends AppCompatActivity
{
    private ListView amigosPorSeleccionar;
    private static String idUser;
    private ArrayList<String> amigosTotales=new ArrayList<>();
    private ArrayList<String> amigosTotaleswithID=new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigos_viaje);
        idUser = MenuBottom.getIdUser();//getIntent().getStringExtra("idKey");
        amigosPorSeleccionar=findViewById(R.id.amigosPSLV);

        amigosTotales=getAmigosTotaless();
        //Adapter LV amigos
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,amigosTotales);
        amigosPorSeleccionar.setAdapter(arrayAdapter);
        //amigos LV
        amigosPorSeleccionar.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent irMenu= new Intent(getApplicationContext(),MenuBottom.class);
                irMenu.putExtra("agregado",amigosTotaleswithID.get(position).toString());
                irMenu.putExtra("idKey",idUser);
                startActivity(irMenu);
            }
        });
    }
    public ArrayList<String> getAmigosTotaless()
    {
        amigosTotales.clear();
        amigosTotaleswithID.clear();
        RequestAsyncAmigos datosUsuario= (RequestAsyncAmigos) new RequestAsyncAmigos().execute();
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
            org.json.simple.JSONObject amigo= (org.json.simple.JSONObject) resultadoPost.get(i);
            String nombre="";
            long idAmigo;
            String telefono;
            nombre= (String) amigo.get("nombre");
            idAmigo= (long) amigo.get("id");
            telefono= (String) amigo.get("telefono");
            amigosTotales.add(nombre+"\n"+telefono);
            amigosTotaleswithID.add(nombre+","+idAmigo+","+telefono);
        }
        return amigosTotales;
    }
    public class RequestAsyncAmigos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Perfil/Amigos",postDataParams);
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
