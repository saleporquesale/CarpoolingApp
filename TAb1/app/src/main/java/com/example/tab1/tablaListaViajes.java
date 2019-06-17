package com.example.tab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

public class tablaListaViajes extends AppCompatActivity {
    String myHtmlString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_lista_viajes);
        WebView mWebView = (WebView) findViewById(R.id.tablaListaViajes);
        myHtmlString=getIntent().getStringExtra("tabla");
        Toast.makeText(getApplicationContext(),myHtmlString,Toast.LENGTH_LONG).show();
        mWebView.loadData(myHtmlString, "text/html", null);
    }
}
