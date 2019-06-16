package com.example.tab1;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class viajesFragment extends Fragment
{
    private View vista;
    private ListView amigosAgregadosLV;
    private String idUser;
    private String agregado="";
    private Spinner autosDisponibles;
    private Button agregarAmigos;
    private Button crearViaje;
    private int pasajerosMaximo=5;
    private static ArrayList<String> autosArray=new ArrayList<>();
    private static ArrayList<String> autosCapacidadAL=new ArrayList<>();
    private static ArrayList<String> amigosAgregadosALID =new ArrayList<>();
    private static ArrayList<String> amigosAgregadosAL =new ArrayList<>();
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
        //Toast.makeText(getContext(),"Pasajeros:"+amigosAgregadosALID.size()+1,Toast.LENGTH_LONG).show();
        //Amigos agregados
        amigosAgregadosLV =vista.findViewById(R.id.amigosAgregadosViajeLV);
        agregarAmigos=vista.findViewById(R.id.agregarAmigosViajeBtn);
        agregado= MenuBottom.getAgregadoViaje();
        //Agregando un nuevo pasajero
        amigosAgregadosALID =agregarPasajero(pasajerosMaximo, amigosAgregadosALID,agregado);
        // Agregar amigos viaje btn
        agregarAmigos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(amigosAgregadosALID.size()<pasajerosMaximo-1)
                {
                    Intent irAgregarAmigos= new Intent(getActivity(),AgregarAmigosViaje.class);
                    startActivity(irAgregarAmigos);
                }
            }
        });
        //LV amigos agregados
        final ArrayAdapter arrayAdapterAmigos=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, amigosAgregadosAL);
        amigosAgregadosLV.setAdapter(arrayAdapterAmigos);
        amigosAgregadosLV.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                dialogo1.setTitle("Eliminar amigo del viaje");
                dialogo1.setMessage("¿ Desea eliminar el amigo seleccionado del viaje ?");
                dialogo1.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id)
                    {
                        amigosAgregadosLV.setAdapter(arrayAdapterAmigos);
                    }
                });
                dialogo1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast.makeText(getContext(),"bueno",Toast.LENGTH_LONG).show();
                    }
                });
                dialogo1.show();

            }
        });
        //Cargar autos
        autosArray=getAutosArray();
        if(autosCapacidadAL.size()>0)
        {

        }
        //Selección del auto
        autosDisponibles=vista.findViewById(R.id.vehiculosSpiner);
        final ArrayAdapter arrayAdapterAutos=new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,autosArray);
        autosDisponibles.setAdapter(arrayAdapterAutos);
        autosDisponibles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                pasajerosMaximo=Integer.parseInt(autosCapacidadAL.get(position));
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
                if((100/pasajerosMaximo)*(amigosAgregadosALID.size()+1)>70)
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
    //Agregando a alguien a la lista del viaje en el caso de que exista algun campo para el
    private ArrayList<String> agregarPasajero(int pasajerosMaximo,ArrayList<String> agregadosWithId,String agregado)
    {
        if(agregado.length()>0)
        {
            if(agregadosWithId.size()<pasajerosMaximo)
            {
                if(!agregadosWithId.contains(agregado))
                {
                    agregadosWithId.add(agregado);
                    ArrayList<String> datos=new ArrayList<>(Arrays.asList(agregado.split(",")));
                    amigosAgregadosAL.add(datos.get(0)+"\nTelefono: "+datos.get(2));
                }
            }
        }
        return agregadosWithId;
    }
    //Array autos
    public ArrayList<String> getAutosArray()
    {
        autosArray.clear();
        autosCapacidadAL.clear();
        RequestAsyncAuto datosUsuario= (RequestAsyncAuto) new RequestAsyncAuto().execute();
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
            org.json.simple.JSONObject auto= (org.json.simple.JSONObject) resultadoPost.get(i);
            String modelo="";
            long numero;
            String placa;
            modelo= (String) auto.get("modelo");
            numero= (long) auto.get("numero");
            placa= (String) auto.get("placa");
            autosCapacidadAL.add(numero+"");
            autosArray.add("Modelo: "+modelo+" Asientos: "+numero+"\n"+" Placa: "+placa);
        }
        return autosArray;
    }
    public class RequestAsyncAuto extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Perfil/Vehiculos",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class RequestAsyncCrearViaje extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());

                return RequestHandler.sendPost(Constante.url+"Perfil/Vehiculos",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }

}
