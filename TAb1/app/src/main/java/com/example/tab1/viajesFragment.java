package com.example.tab1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class viajesFragment extends Fragment
{
    private View vista;
    private ListView amigosAgregados;
    private String idUser;
    private Spinner autosDisponibles;
    private String autoSeleccionado;
    private Button agregarAmigos;
    private Button crearViaje;
    private int pasajerosMaximo=5;

    public viajesFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Generales
        vista=inflater.inflate(R.layout.fragment_viajes, container, false);
        idUser=MenuBottom.getIdUser();
        crearViaje=vista.findViewById(R.id.crearViajeBtn);
        //Amigos agregados
        amigosAgregados=vista.findViewById(R.id.amigosAgregadosViajeLV);
        agregarAmigos=vista.findViewById(R.id.agregarAmigosViajeBtn);
        agregarAmigos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(MenuBottom.getAmigosDisponibles().size()>0
                        && MenuBottom.getAmigosAgregadosAL().size()<pasajerosMaximo-1)
                {
                    Intent irAgregarAmigos= new Intent(getActivity(),AgregarAmigosViaje.class);
                    startActivity(irAgregarAmigos);
                }
                else
                {
                    if(MenuBottom.getAmigosDisponibles().size()==0)
                    {
                        Toast.makeText(getContext(),"No tienes más amigos disponibles",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(),"No tienes más asientos disponibles," +
                                " puedes tocar algun amigo para eliminarlo y agregar otro"
                                ,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        final ArrayAdapter arrayAdapterAmigos=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,MenuBottom.getAmigosAgregadosAL());
        amigosAgregados.setAdapter(arrayAdapterAmigos);
        amigosAgregados.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                MenuBottom.addAmigosDisponiblesAL(MenuBottom.getAmigosAgregadosAL().get(position));
                MenuBottom.removeAmigosAgregadosAL(MenuBottom.getAmigosAgregadosAL().get(position));
                amigosAgregados.setAdapter(arrayAdapterAmigos);
            }
        });
        //Selección del auto
        autosDisponibles=vista.findViewById(R.id.vehiculosSpiner);
        final ArrayAdapter arrayAdapterAutos=new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,MenuBottom.getAutosArray());
        autoSeleccionado=MenuBottom.getAutosArray().get(1);
        autosDisponibles.setAdapter(arrayAdapterAutos);
        autosDisponibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                autoSeleccionado= MenuBottom.getAutosArray().get(position);
                //Toast.makeText(getContext(),autoSeleccionado,Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        //Crear Viaje
        crearViaje.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if((100/pasajerosMaximo)*(MenuBottom.getAmigosAgregadosAL().size()+1)>70)
                {
                    Toast.makeText(getContext(),"Creando viaje",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext()
                            , "Debes invitar más pasajeros para poder crear el viaje"
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return vista;
    }
}
