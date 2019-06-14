package com.example.tab1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class notificacionesFragment extends Fragment {
    private View vista;
    public ListView notificaciones;
    public String notificacionesEntrada;
    private String idUser;
    private ArrayList<String> notificacionesArray;
    public notificacionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_notificaciones, container, false);
        notificaciones=vista.findViewById(R.id.notificacionesLV);
        idUser=MenuBottom.getIdUser();
        notificacionesEntrada="Noti1\nPromete,Noti2\nNoPromete,Noti3\nCosas";
        notificacionesArray=new ArrayList<>(Arrays.asList(notificacionesEntrada.split(",")));
        final ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,notificacionesArray);
        notificaciones.setAdapter(arrayAdapter);
        notificaciones.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                /*Toast.makeText(parent.getContext()
                        ,"Selecciona:" +parent.getItemAtPosition(position).toString()
                        ,Toast.LENGTH_SHORT).show();
                */
                /* Borrar notificaciones al tocarlas*/
                notificacionesArray.remove(position);
                notificaciones.setAdapter(arrayAdapter);
                Toast.makeText(parent.getContext()
                        ,"A: "+idUser
                        ,Toast.LENGTH_SHORT).show();
            }
        });
        return vista;
    }

}
