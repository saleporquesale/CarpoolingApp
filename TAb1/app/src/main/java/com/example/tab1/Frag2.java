package com.example.tab1;

import com.example.tab1.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.content.Intent;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Frag2 extends Fragment {
    private EditText nombre;
    private EditText correo;
    private Button registrarme;
    private EditText id;
    private View vista;
    private EditText telefono;
    CallbackManager callbackManager;
    private boolean facebook=false;

    public Frag2()
    {


        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {   //CallbackManager callbackManager;
        vista =inflater.inflate(R.layout.fragment_f2, container, false);
        nombre=(EditText)vista.findViewById(R.id.nombreET);
        correo=(EditText)vista.findViewById(R.id.correoInsETR);
        registrarme=(Button)vista.findViewById(R.id.registrarmeBtn);
        id=(EditText)vista.findViewById(R.id.idETL);
        telefono=(EditText)vista.findViewById(R.id.telefonoET);
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton)vista.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        //AccessToken accessToken = AccessToken.getCurrentAccessToken();
        //boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        //LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult)
            {

                facebook=true;
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        registrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean resultado=comprobarNombre();
                resultado=comprobarEmail()&&resultado;
                resultado=comprobarId()&&resultado;
                resultado=comprobarTelefono()&&resultado;
                resultado=comprobarFacebook()&&resultado;
                if(resultado)
                {
                    RequestAsync post2= (RequestAsync) new RequestAsync().execute();
                    String resultadoPost= null;
                    try {
                        resultadoPost = post2.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (resultadoPost.contains("OK"))
                    {
                        Toast.makeText(getContext(),"Registro completado",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // Inflate the layout for this fragment

        return vista;
    }

    private boolean comprobarFacebook()
    {
        if(!facebook)
        {
            Toast.makeText(getContext(),"Es necesario vincular tu cuenta de Facebook para crear una cuenta",Toast.LENGTH_LONG).show();
        }
        return facebook;
    }


    public boolean comprobarEmail()
    {
        boolean resultado=false;
        String test=correo.getText().toString();
        //resultado=test.matches("\\w+@xtec.ac.cr");
        resultado=ComprobarDatos.isValidEmail(test);
        if(!resultado)
        {
            correo.setText("");
            correo.setHint("Introduzca su correo institucional");
        }
        return resultado;
    }
    private boolean comprobarNombre()
    {
        String test=nombre.getText().toString();
        boolean resultado=test.length()>0;
        if(!resultado)
        {
            nombre.setHint("Introduzca su nombre");
        }
        return resultado;
    }
    private boolean comprobarId()
    {
        String test=id.getText().toString();
        boolean resultado=test.length()>0;
        if(!resultado)
        {
            id.setHint("Introduzca su id");
        }
        return resultado;
    }
    private boolean comprobarTelefono()
    {
        String test=telefono.getText().toString();
        boolean resultado=test.length()>0;
        if(!resultado)
        {
            telefono.setHint("Introduzca su teléfono");
        }
        return resultado;
    }
    public class RequestAsync extends AsyncTask<String,String,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nombre", nombre.getText().toString());
                postDataParams.put("id", id.getText().toString());
                postDataParams.put("telefono", telefono.getText().toString());
                postDataParams.put("correo", correo.getText().toString());
                return RequestHandler.sendPost(Constante.url+"SignUp",postDataParams);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
