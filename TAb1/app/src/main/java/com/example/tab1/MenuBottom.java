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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class MenuBottom extends AppCompatActivity {
    private static String idUser;
    private FragmentManager fm = getSupportFragmentManager();
    private static String agregadoViaje;


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
        agregadoViaje=getIntent().getStringExtra("agregado");
        Toast.makeText(this, idUser,Toast.LENGTH_SHORT).show();
        fm.beginTransaction().replace(R.id.pista,new viajesFragment()).commit();

    }
    public static String getIdUser() {return idUser;}
    public static String getAgregadoViaje()
    {
        if(agregadoViaje==null)
        {
            agregadoViaje="";
        }
        return agregadoViaje;
    }
    public static String setAgregadoViaje(String agregado)
    {
        return agregado;
    }
}
