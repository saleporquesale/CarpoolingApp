package com.example.tab1;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class userFragment extends Fragment {
    private View vista;
    private EditText nombreET;
    private EditText idET;
    private EditText telefonoET;
    private EditText correoInstitucionalET;
    private EditText montoQRET;
    private ListView autosLV;
    private Button guardarPerfilBtn;
    private Button generarQRBtn;
    private ImageButton agregarAutoBtn;
    private ImageView codigoQR;

    public userFragment()
    {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista=inflater.inflate(R.layout.fragment_user, container, false);
        nombreET=vista.findViewById(R.id.nombreUserET);
        idET=vista.findViewById(R.id.idUET);
        telefonoET=vista.findViewById(R.id.telefonoUET);
        correoInstitucionalET=vista.findViewById(R.id.correoUET);
        montoQRET=vista.findViewById(R.id.montoUET);
        guardarPerfilBtn=vista.findViewById(R.id.guardarPerfilBtn);
        generarQRBtn=vista.findViewById(R.id.qrCodeBtn);
        agregarAutoBtn=vista.findViewById(R.id.addCarrosUBtn);
        codigoQR=vista.findViewById(R.id.image);
        //Solicitando datos de usuario
        RequestAsyncDatos datosUsuario= (RequestAsyncDatos) new RequestAsyncDatos().execute();
        JSONParser parser = new JSONParser();
        JSONObject resultadoPost=new JSONObject();
        try
        {
            resultadoPost = (JSONObject) parser.parse(datosUsuario.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Escribiendo los datos obtenidos
        idET.setText(MenuBottom.getIdUser());
        try {
            nombreET.setText(resultadoPost.getString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            telefonoET.setText(resultadoPost.getString("telefono"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            correoInstitucionalET.setText(resultadoPost.getString("correo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Agregar autos
        agregarAutoBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent irAgregarAuto= new Intent(getActivity(),AgregarAuto.class);
                irAgregarAuto.putExtra("idKey",MenuBottom.getIdUser());
                startActivity(irAgregarAuto);
            }
        });
        //Guardar Perfil
        guardarPerfilBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                RequestAsyncActualizarDatos datosUsuario= (RequestAsyncActualizarDatos) new RequestAsyncActualizarDatos().execute();
                String resultadoPost= null;
                try {
                    resultadoPost = datosUsuario.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //Crear Codigo
        generarQRBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String text2Qr = montoQRET.getText().toString().trim()+"\n"+MenuBottom.getIdUser();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,600,600);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    codigoQR.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });
        return vista;
    }
    public class RequestAsyncActualizarDatos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nombre", nombreET.getText().toString());
                postDataParams.put("id", idET.getText().toString());
                postDataParams.put("telefono", telefonoET.getText().toString());
                postDataParams.put("correo", correoInstitucionalET.getText().toString());
                return RequestHandler.sendPost(Constante.url+"Perfil/Guardar",postDataParams);
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
    public class RequestAsyncDatos extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", MenuBottom.getIdUser());
                return RequestHandler.sendPost(Constante.url+"Perfil",postDataParams);
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
