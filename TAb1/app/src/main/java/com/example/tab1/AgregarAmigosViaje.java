package com.example.tab1;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AgregarAmigosViaje extends AppCompatActivity
{
    private ListView amigosPorSeleccionar;
    private static String idUser;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigos_viaje);
        idUser = MenuBottom.getIdUser();//getIntent().getStringExtra("idKey");
        amigosPorSeleccionar=findViewById(R.id.amigosPSLV);
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,MenuBottom.getAmigosDisponibles());
        amigosPorSeleccionar.setAdapter(arrayAdapter);
        amigosPorSeleccionar.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                MenuBottom.addAmigosAgregadosAL(MenuBottom.getAmigosDisponibles().get(position));
                MenuBottom.removeAmigosDisponiblesAL(position);
                Intent irMenu= new Intent(getApplicationContext(),MenuBottom.class);
                startActivity(irMenu);
            }
        });
    }
}
