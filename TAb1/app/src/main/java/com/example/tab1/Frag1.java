package com.example.tab1;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag1 extends Fragment {
    private View vista;
    private EditText id;
    private EditText password;
    private Button iniciarSesion;
    public Frag1()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        vista=inflater.inflate(R.layout.fragment_f1, container, false);
        id=(EditText)vista.findViewById(R.id.idETL);
        password=(EditText)vista.findViewById(R.id.passwordETL);
        iniciarSesion=(Button)vista.findViewById(R.id.iniciarSesionBtn);
        //Boton inicio de sesión
        iniciarSesion.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                boolean resultado=comprobarId();
                resultado=comprobarPassword()&&resultado;
                if(resultado)
                {
                    RequestAsync post= (RequestAsync) new RequestAsync().execute();
                    String resultadoPost= null;
                    try {
                        resultadoPost = post.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(resultadoPost.contains("OK"))
                        {
                            Toast.makeText(getContext(),"Iniciando",Toast.LENGTH_SHORT).show();
                            Intent irMenu= new Intent(getActivity(),MenuBottom.class);
                            irMenu.putExtra("idKey",id.getText().toString());
                            startActivity(irMenu);
                        }
                }
            }
        });
        // Inflate the layout for this fragment
        return vista;
    }
    private boolean comprobarPassword()
    {
        String test=password.getText().toString();
        boolean resultado=test.matches("\\w+");
        if(!resultado)
        {
            password.setHint("Introduzca su contraseña");
        }
        return resultado;
    }
    private boolean comprobarId()
    {
        String test=id.getText().toString();
        boolean resultado=test.matches("\\w+");
        if(!resultado)
        {
            id.setHint("Introduzca su id");
        }
        return resultado;
    }
    public class RequestAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("http://172.18.150.4:9090/");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("id", id.getText().toString());
                postDataParams.put("clave", password.getText().toString());
                return RequestHandler.sendPost(Constante.url,postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if(s!=null){
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        }


    }

}
