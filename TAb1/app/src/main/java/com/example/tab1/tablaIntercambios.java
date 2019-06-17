package com.example.tab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class tablaIntercambios extends AppCompatActivity {
    String myHtmlString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_intercambios);
        WebView mWebView = (WebView) findViewById(R.id.tablaListaIntercambios);
        myHtmlString=getIntent().getStringExtra("my");
        mWebView.loadData(myHtmlString, "text/html", null);
    }
}
