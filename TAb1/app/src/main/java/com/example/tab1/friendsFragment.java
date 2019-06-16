package com.example.tab1;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.Collections;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class friendsFragment extends Fragment {

    private View vista;
    private ArrayList<String> amigosTotalesWithId =new ArrayList<>();
    private ArrayList<String> amigosTotales =new ArrayList<>();
    private ArrayList<String> amigosPosiblesBuscados=new ArrayList<>();
    private String idUser;
    private ListView amigos;
    private ImageButton buscarBtn;
    private ImageButton buscarNuevoBtn;
    private EditText amigosET;
    private String idAmigoSolicitud;
    private boolean amigosAgregados=true;
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
        idUser=MenuBottom.getIdUser();
        // LV amigos
        amigosTotales =getAmigosTotaless();
        final ArrayAdapter adapterAgregados=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, amigosTotales);
        amigos.setAdapter(adapterAgregados);
        amigos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (amigosAgregados)
                {
                    Toast.makeText(getContext(), amigosTotales.get(position),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Obteniendo el idAmigo
                    ArrayList<String> datosSelecionado= new ArrayList<String>(Arrays.asList(
                            amigosPosiblesBuscados.get(position).split("\n")));
                    idAmigoSolicitud=datosSelecionado.get(1);
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                    dialogo1.setTitle("Solicitud de amistad");
                    dialogo1.setMessage("¿ Acepta la solicitud de amistad ?");
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            //Enviando el idAmigo y idUser
                            RequestAsyncSolicitudAmigo datosUsuario= (RequestAsyncSolicitudAmigo)
                                    new RequestAsyncSolicitudAmigo().execute();
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
                            amigosAgregados=true;
                            amigos.setAdapter(adapterAgregados);
                        }
                    });
                    dialogo1.setNegativeButton("Negar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Toast.makeText(getContext(),"bueno",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogo1.show();
                }
            }
        });
        // Btn buscarAmigoAgregados
        buscarBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (amigosET.getText()!=null)
                {
                    String buscado= amigosET.getText().toString();
                    ArrayList<String> buscados=getSearchAmigosTotalesAL(buscado, amigosTotalesWithId);
                    final ArrayAdapter adapterAgregadosBusqueda=new ArrayAdapter(getContext(),
                            android.R.layout.simple_list_item_1,buscados);
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
                    amigosAgregados=false;
                    amigosPosiblesBuscados=getAmigosPosiblesBuscados();
                    final ArrayAdapter adapterNew=new ArrayAdapter(getContext(),
                            android.R.layout.simple_list_item_1, amigosPosiblesBuscados);
                    amigos.setAdapter(adapterNew);
                }
            }
        });
        return vista;
    }

    public static ArrayList<String> getSearchAmigosTotalesAL(String buscado,ArrayList<String> amigos)
    {
        ArrayList<String> buscados=new ArrayList<>();;
        for (int i=0;i<amigos.size();i++)
        {
            if(amigos.get(i).toLowerCase().trim().contains(buscado.toLowerCase().trim()))
            {
                buscados.add(amigos.get(i));
            }
        }
        return buscados;
    }
    private ArrayList<String> getAmigosPosiblesBuscados()
    {
        amigosPosiblesBuscados.clear();
        RequestAsyncNewAmigo datosUsuario= (RequestAsyncNewAmigo) new RequestAsyncNewAmigo().execute();
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
            amigosPosiblesBuscados.add(nombre+"\n"+idAmigo+"\n"+telefono);
        }
        return amigosPosiblesBuscados;
    }

    public ArrayList<String> getAmigosTotaless()
    {
        amigosTotalesWithId.clear();
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
            amigosTotalesWithId.add(nombre+","+idAmigo+","+telefono);
            amigosTotales.add(nombre+"\nTelefono:"+telefono);
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
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class RequestAsyncNewAmigo extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nombre", amigosET.getText().toString());
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Perfil/Amigos/Buscar",postDataParams);
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
    public class RequestAsyncSolicitudAmigo extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                postDataParams.put("idAmigo",idAmigoSolicitud);
                return RequestHandler.sendPost(Constante.url+"Perfil/Amigos/Buscar/Agregar",postDataParams);
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
