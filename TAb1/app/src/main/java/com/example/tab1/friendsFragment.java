package com.example.tab1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class friendsFragment extends Fragment {

    private View vista;
    private ArrayList<String> amigosDisponiblesAL=new ArrayList<>();
    private ArrayList<String> amigosTotales=new ArrayList<>();
    private ArrayList<String> amigosPosiblesBuscados=new ArrayList<>();
    private String idUser;
    private ListView amigos;
    private Button buscarBtn;
    private Button buscarNuevoBtn;
    private EditText amigosET;
    public friendsFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //generales
        vista=inflater.inflate(R.layout.fragment_friends, container, false);
        amigos=vista.findViewById(R.id.amigosLV);
        buscarBtn=vista.findViewById(R.id.buscarAmigosBtn);
        buscarNuevoBtn=vista.findViewById(R.id.buscarNewAmigoBtn);
        amigosET=vista.findViewById(R.id.amigosET);
        amigosDisponiblesAL=MenuBottom.getAmigosDisponibles();
        amigosTotales.addAll(MenuBottom.getAmigosAgregadosAL());
        amigosTotales.addAll(amigosDisponiblesAL);
        Collections.sort(amigosTotales);
        idUser=MenuBottom.getIdUser();
        // LV amigos
        final ArrayAdapter adapterAgregados=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, MenuBottom.getAmigosTotalesAL());
        amigos.setAdapter(adapterAgregados);
        amigos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
              Toast.makeText(getContext(),MenuBottom.getPositionAmigosTotalesAL(position)
                      ,Toast.LENGTH_SHORT).show();
            }
        });
        // Btn buscarAmigo
        buscarBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (amigosET.getText()!=null)
                {
                    String buscado= amigosET.getText().toString();
                    final ArrayAdapter adapterAgregadosBusqueda=new ArrayAdapter(getContext(),
                            android.R.layout.simple_list_item_1, MenuBottom.getSearchAmigosTotalesAL(buscado));
                    amigos.setAdapter(adapterAgregadosBusqueda);
                }
            }
        });
        // Btn buscarNewAmigo
        buscarNuevoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!amigosET.getText().toString().equals(""))
                {
                    final ArrayAdapter adapterNew=new ArrayAdapter(getContext(),
                            android.R.layout.simple_list_item_1, amigosPosiblesBuscados);
                    amigos.setAdapter(adapterNew);
                }
            }
        });
        return vista;
    }

}
