package com.example.tab1;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
public class notificacionesFragment extends Fragment {
    private View vista;
    public ListView notificaciones;
    public String notificacionesEntrada;
    private String idUser;
    private ArrayList<String> notificacionesArray=new ArrayList<>();
    private ArrayList<String> notificacionesALCompleto=new ArrayList<>();
    private String idNotificacion="-1";
    String idViaje = "";
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
        notificacionesArray=getNotificacionesArray();
        final ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1,notificacionesArray);
        notificaciones.setAdapter(arrayAdapter);
        notificaciones.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<String> datosNotificacion=new ArrayList<>(Arrays.asList
                        (notificacionesALCompleto.get(position).split(",")));
                //Obteniendo datos
                int tipo=Integer.parseInt(datosNotificacion.get(2));
                idNotificacion=datosNotificacion.get(1);
                if(tipo==0)//Solicitud de amistad
                {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
                    dialogo1.setTitle("Solicitud de amistad");
                    dialogo1.setMessage("¿ Acepta la solicitud de amistad ?");
                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            RequestAsyncAceptarAmistad datosUsuario= (RequestAsyncAceptarAmistad)
                                    new RequestAsyncAceptarAmistad().execute();
                            JSONParser parser = new JSONParser();
                            JSONArray resultadoPost=new JSONArray();
                        }
                    });
                    dialogo1.setNegativeButton("Negar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogo1, int id) {
                            Toast.makeText(getContext(),"bueno",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogo1.show();

                }
                else if (tipo==2)//Solicitud de viaje
                {
                    AlertDialog.Builder dialogo2 = new AlertDialog.Builder(getContext());
                    dialogo2.setTitle("Solicitud de viaje");
                    dialogo2.setMessage("¿ Desea aceptar el viaje ?");
                    dialogo2.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            RequestAsyncAceptarViaje datosUsuario= (RequestAsyncAceptarViaje)
                                    new RequestAsyncAceptarViaje().execute();
                        }
                    });
                    dialogo2.setNegativeButton("Negar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            RequestAsyncRechazarViaje datosUsuario=(RequestAsyncRechazarViaje)
                                    new RequestAsyncRechazarViaje().execute();
                        }
                    });
                    dialogo2.show();
                }
                else if (tipo==8)//Iniciar el viaje
                {
                    AlertDialog.Builder dialogo3 = new AlertDialog.Builder(getContext());
                    dialogo3.setTitle("Iniciar viaje");
                    dialogo3.setMessage("¿ Desea iniciar el viaje ?");
                    dialogo3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            RequestAsyncIniciarViaje datosUsuario= (RequestAsyncIniciarViaje)
                                    new RequestAsyncIniciarViaje().execute();
                            JSONParser parser = new JSONParser();
                            JSONArray resultadoPost=new JSONArray();
                        }
                    });
                    dialogo3.setNegativeButton("Negar", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialogo1, int id)
                        {
                            Toast.makeText(getContext(),"bueno",Toast.LENGTH_LONG).show();
                        }
                    });
                    dialogo3.show();
                }
                else if (tipo==9)
                {

                }

            }
        });
        return vista;
    }

    private ArrayList<String> getNotificacionesArray()
    {
        notificacionesArray.clear();
        notificacionesALCompleto.clear();
        RequestAsyncRecibirNotificaciones datosUsuario= (RequestAsyncRecibirNotificaciones)
                new RequestAsyncRecibirNotificaciones().execute();
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
            String notificacion="";
            long idNotificacion;
            long idTipo;
            idTipo= (long) amigo.get("tipo");
            notificacion= (String) amigo.get("mensaje");
            if(idTipo==2||idTipo==8)
            {
                idViaje = notificacion.substring(notificacion.lastIndexOf("#")+1);
                notificacion = notificacion.substring(0,notificacion.lastIndexOf("#"));
            }
            idNotificacion= (long) amigo.get("id");
            notificacionesArray.add(0,notificacion);
            notificacionesALCompleto.add(0,notificacion+","+idNotificacion+","+idTipo);
        }
        return notificacionesArray;
    }

    public class RequestAsyncRecibirNotificaciones extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Notificaciones",postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null)
            {
                //Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class RequestAsyncAceptarAmistad extends AsyncTask<String,String,String>
    {
        @Override////////////
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                postDataParams.put("idNotificacion", idNotificacion);
                return RequestHandler.sendPost(Constante.url+"Perfil/Amigos/Aceptar",postDataParams);
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
    public class RequestAsyncAceptarViaje extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                postDataParams.put("idNotificacion", idNotificacion);
                postDataParams.put("idViaje", idViaje);
                return RequestHandler.sendPost(Constante.url+"Viaje/Aceptar",postDataParams);
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
    public class RequestAsyncRechazarViaje extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                postDataParams.put("idNotificacion", idNotificacion);
                postDataParams.put("idViaje", idViaje);
                return RequestHandler.sendPost(Constante.url+"Viaje/Rechazar",postDataParams);
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
    public class RequestAsyncIniciarViaje extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                postDataParams.put("idNotificacion", idNotificacion);
                postDataParams.put("idViaje", idViaje);
                return RequestHandler.sendPost(Constante.url+"Viaje/Iniciar",postDataParams);
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
