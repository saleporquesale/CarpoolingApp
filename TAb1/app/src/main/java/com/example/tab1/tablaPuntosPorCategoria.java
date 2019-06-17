package com.example.tab1;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class tablaPuntosPorCategoria extends AppCompatActivity {

    String myHtmlString;
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabla_puntos_por_categoria);
        mWebView = (WebView) findViewById(R.id.tablaPuntosPorCategoria);
        myHtmlString=getIntent().getStringExtra("my");
        mWebView.loadData(myHtmlString, "text/html", null);
    }
}
