package com.example.tab1;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class dashboardFragment extends Fragment {

    private View vista;
    private BarChart diferenciaPasajeroChofer;
    private BarChart viajesPorMes;
    private Button tablaPuntosPorCategoriaBtn;
    private Button listaIntercambiosBtn;
    private Button listaVijaesRealizadosBtn;
    private String my;
    private String tablaListaViajes;
    private String tablaIntercambios;
    private TextView CantidadDePuntosTV;
    public dashboardFragment()
    {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_dashboard, container, false);
        diferenciaPasajeroChofer =vista.findViewById(R.id.diferenciaPasajeroChofer);
        viajesPorMes=vista.findViewById(R.id.viajesPorMes);
        listaIntercambiosBtn=vista.findViewById(R.id.listaIntercambiosBtn);
        tablaPuntosPorCategoriaBtn=vista.findViewById(R.id.tablaPuntosXcategoriaBtn);
        listaVijaesRealizadosBtn=vista.findViewById(R.id.listaViajesBtn);
        CantidadDePuntosTV=vista.findViewById(R.id.idCantidadDePuntos);

        RequestAsyncTablaPorCategoria pidiendoTablaCategorias= (RequestAsyncTablaPorCategoria)
                new RequestAsyncTablaPorCategoria().execute();
        RequestAsyncListaViajes pidiendoTablaListaViejas= (RequestAsyncListaViajes)
                new RequestAsyncListaViajes().execute();
//        RequestAsyncListaIntercambios pidiendoTablaListaIntercambio= (RequestAsyncListaIntercambios)
//                new RequestAsyncListaIntercambios().execute();
        RequestAsyncPedirPuntos datosUsuario= (RequestAsyncPedirPuntos)
                new RequestAsyncPedirPuntos().execute();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject resultadoPost=new org.json.simple.JSONObject();
        try
        {
            resultadoPost = (org.json.simple.JSONObject) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<BarEntry> dataVals1=new ArrayList<BarEntry>();
        long puntaje= (long) resultadoPost.get("Puntaje");
        CantidadDePuntosTV.setText("Puntos disponibles: "+puntaje);


        //Boton tabla puntos por categoria
        tablaPuntosPorCategoriaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent irTabla2= new Intent(getActivity(),tablaPuntosPorCategoria.class);
                irTabla2.putExtra("my",my);
                startActivity(irTabla2);
            }
        });
        //Boton tabla lista de viajes
        listaVijaesRealizadosBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent irTabla1= new Intent(getActivity(),tablaListaViajes.class);
                irTabla1.putExtra("tabla",tablaListaViajes);
                startActivity(irTabla1);
            }
        });
        //Boton tabla listaIntercambios
        listaIntercambiosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent irTabla3= new Intent(getActivity(),tablaIntercambios.class);
                irTabla3.putExtra("tabla",tablaIntercambios);
                startActivity(irTabla3);
            }
        });
        diferenciaPasajeroChofer();
        viajesPorMes();
        return vista;
    }
    private void viajesPorMes()
    {
        viajesPorMes.setDrawBarShadow(false);
        viajesPorMes.setDrawValueAboveBar(true);
        viajesPorMes.setMaxVisibleValueCount(50);
        viajesPorMes.setPinchZoom(true);
        viajesPorMes.setDoubleTapToZoomEnabled(false);
        viajesPorMes.setDrawGridBackground(true);
        viajesPorMes.setFitBars(true);
        ArrayList<BarEntry> dataVals=new ArrayList<BarEntry>();
        RequestAsyncViajesPorMes datosUsuario= (RequestAsyncViajesPorMes)
                new RequestAsyncViajesPorMes().execute();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject resultadoPost=new org.json.simple.JSONObject();
        try
        {
            resultadoPost = (org.json.simple.JSONObject) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<BarEntry> dataVals1=new ArrayList<BarEntry>();
        long sem1= (long) resultadoPost.get("Primera");
        long sem2= (long) resultadoPost.get("Segunda");
        long sem3= (long) resultadoPost.get("Tercera");
        long sem4= (long) resultadoPost.get("Cuarta");
        dataVals1.add(new BarEntry(1,sem1));
        dataVals1.add(new BarEntry(2,sem2));
        dataVals1.add(new BarEntry(3,sem3));
        dataVals1.add(new BarEntry(4,sem4));
        BarDataSet barDataSet=new BarDataSet(dataVals1,"1");
        int[] azules ={ Color.rgb(41, 121, 255),Color.rgb(117, 167, 255)};
        barDataSet.setColors(azules );
        BarData data= new BarData(barDataSet);
        data.setBarWidth(0.9f);
        viajesPorMes.setData(data);
    }

    private void diferenciaPasajeroChofer()
    {
        diferenciaPasajeroChofer.setDrawBarShadow(false);
        diferenciaPasajeroChofer.setDrawValueAboveBar(true);
        diferenciaPasajeroChofer.setMaxVisibleValueCount(50);
        diferenciaPasajeroChofer.setPinchZoom(true);
        diferenciaPasajeroChofer.setDoubleTapToZoomEnabled(false);
        diferenciaPasajeroChofer.setDrawGridBackground(true);
        diferenciaPasajeroChofer.setFitBars(true);
        RequestAsyncDiferenciaChoferesPasajeros datosUsuario= (RequestAsyncDiferenciaChoferesPasajeros)
                new RequestAsyncDiferenciaChoferesPasajeros().execute();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONObject resultadoPost=new org.json.simple.JSONObject();
        try
        {
            resultadoPost = (org.json.simple.JSONObject) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<BarEntry> dataVals1=new ArrayList<BarEntry>();
        long Chofer1= (long) resultadoPost.get("Chofer1");
        long Chofer2= (long) resultadoPost.get("Chofer2");
        long Chofer3= (long) resultadoPost.get("Chofer3");
        long Chofer4= (long) resultadoPost.get("Chofer4");
        long Pasajero1= (long) resultadoPost.get("Pasajero1");
        long Pasajero2= (long) resultadoPost.get("Pasajero2");
        long Pasajero3= (long) resultadoPost.get("Pasajero3");
        long Pasajero4= (long) resultadoPost.get("Pasajero4");
        dataVals1.add(new BarEntry(1,new float[]{Chofer1,Pasajero1}));
        dataVals1.add(new BarEntry(1,new float[]{Chofer2,Pasajero2}));
        dataVals1.add(new BarEntry(1,new float[]{Chofer3,Pasajero3}));
        dataVals1.add(new BarEntry(1,new float[]{Chofer4,Pasajero4}));
        BarDataSet barDataSet=new BarDataSet(dataVals1,"1");
        int[] azules ={ Color.rgb(41, 121, 255),Color.rgb(117, 167, 255)};
        barDataSet.setColors(azules );
        BarData data= new BarData(barDataSet);
        data.setBarWidth(0.9f);
        diferenciaPasajeroChofer.setData(data);
    }
    public class RequestAsyncDiferenciaChoferesPasajeros extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Estadistica/Diferencia/Semana",postDataParams);
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
    public class RequestAsyncViajesPorMes extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Estadistica/Viajes/Semana",postDataParams);
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
    public class RequestAsyncTablaPorCategoria extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Estadistica/Puntaje",postDataParams);
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
                my=s;
            }
        }
    }
    public class RequestAsyncListaViajes extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Estadistica/Historial",postDataParams);
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
                tablaListaViajes=s;
                Toast.makeText(getContext(), "Obtenido:"+tablaListaViajes, Toast.LENGTH_LONG).show();
            }
        }
    }
    public class RequestAsyncListaIntercambios extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"pendiente",postDataParams);
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
                tablaIntercambios=s;
            }
        }
    }
    public class RequestAsyncPedirPuntos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Perfil/Puntos",postDataParams);
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
                tablaIntercambios=s;
            }
        }
    }
}
