package com.example.tab1;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONObject;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MenuBottom extends AppCompatActivity {
    private static String idUser;
    private FragmentManager fm = getSupportFragmentManager();
    private static ArrayList<String> amigosDisponiblesAL=new ArrayList<>();
    private static ArrayList<String> amigosAgregadosAL=new ArrayList<>();
    private static ArrayList<String> amigosTotalesAL=new ArrayList<>();
    private static ArrayList<String> autosArray=new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_viajes:
                    fm.beginTransaction().replace(R.id.pista,new viajesFragment()).commit();
                    return true;
                case R.id.navigation_amigos:
                    fm.beginTransaction().replace(R.id.pista,new friendsFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    fm.beginTransaction().replace(R.id.pista,new dashboardFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    fm.beginTransaction().replace(R.id.pista,new notificacionesFragment()).commit();
                    return true;
                case R.id.navigation_usuario:
                    fm.beginTransaction().replace(R.id.pista,new userFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bottom);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        idUser=getIntent().getStringExtra("idKey");
        Toast.makeText(this, idUser,Toast.LENGTH_SHORT).show();
        fm.beginTransaction().replace(R.id.pista,new viajesFragment()).commit();
        //Datos provisionales
        if(amigosDisponiblesAL.size()==0)
        {
            amigosDisponiblesAL.add("Carlos");
            amigosDisponiblesAL.add("Antonio");
            amigosDisponiblesAL.add("Franco");
            amigosDisponiblesAL.add("Fran");
            amigosDisponiblesAL.add("Ana");
        }
        if(autosArray.size()==0)
        {
            autosArray.add("Perol");
            autosArray.add("Carcacha");
            autosArray.add("mejorEsNada");
        }
        //Datos provisionales
    }
    public static String getIdUser()
    {
        return idUser;
    }
    //Manejo de arrays de amigos
    //
    //Array Agregados
    public static ArrayList<String> getAmigosAgregadosAL()
    {
        Collections.sort(amigosAgregadosAL);
        return amigosAgregadosAL;
    }
    public static String getPositionAmigosAgregadosAL(int position)
    {
        return amigosAgregadosAL.get(position);
    }
    public static void addAmigosAgregadosAL(String nuevoAmigo)
    {
        amigosAgregadosAL.add(nuevoAmigo);
    }
    public static void removeAmigosAgregadosAL(String amigo)
    {
        amigosAgregadosAL.remove(amigo);
    }
    public static void removeAmigosAgregadosAL(int amigo)
    {
        amigosAgregadosAL.remove(amigo);
    }
    //Array Disponibles
    public static ArrayList<String> getAmigosDisponibles()
    {
        Collections.sort(amigosDisponiblesAL);
        return amigosDisponiblesAL;
    }
    public static String getPositionAmigosDisponiblesAL(int position)
    {
        return amigosDisponiblesAL.get(position);
    }
    public static void addAmigosDisponiblesAL(String nuevoAmigo)
    {
        amigosDisponiblesAL.add(nuevoAmigo);
    }
    public static void removeAmigosDisponiblesAL(String amigo)
    {
        amigosDisponiblesAL.remove(amigo);
    }
    public static void removeAmigosDisponiblesAL(int amigo)
    {
        amigosDisponiblesAL.remove(amigo);
    }
    //Array Totales
    public static ArrayList<String> getAmigosTotalesAL()
    {
        amigosTotalesAL.clear();
        amigosTotalesAL.addAll(getAmigosDisponibles());
        amigosTotalesAL.addAll(getAmigosAgregadosAL());
        Collections.sort(amigosTotalesAL);
        return amigosTotalesAL;
    }
    public static ArrayList<String> getSearchAmigosTotalesAL(String buscado)
    {
        amigosTotalesAL=getAmigosTotalesAL();
        ArrayList<String> buscados=new ArrayList<>();;
        for (int i=0;i<amigosTotalesAL.size();i++)
        {
            if(amigosTotalesAL.get(i).toLowerCase().trim().contains(buscado.toLowerCase().trim()))
            {
                buscados.add(amigosTotalesAL.get(i));
            }
        }
        return buscados;
    }
    public static String getPositionAmigosTotalesAL(int position)
    {
        return amigosTotalesAL.get(position);
    }
    //Array autos
    public static ArrayList<String> getAutosArray()
    {
        return autosArray;
    }
    public class RequestAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", idUser);
                return RequestHandler.sendPost("http://172.18.1.184:9090/SignUp",postDataParams);
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
