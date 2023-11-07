package com.codebrain.luckytimes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.*;
import android.view.WindowManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.*;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.*;

import com.ahmedelsayed.sunmiprinterutill.PrintMe;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.URL;

public class Principal extends AppCompatActivity {

    private PrintMe printMe;
    WebView webView, webViewPrint;
    private ProgressDialog progressDialog;
    ImageView img;

    Revision r = new Revision();

    FloatingActionButton btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toast.makeText(this, "¡Se ha autenticado el dispositivo!", Toast.LENGTH_LONG).show();

        printMe =  new PrintMe(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando... Un momento por favor.");
        progressDialog.show();
        //progressDialog.setMessage("Cargando Servidor LuckyTimes... Un momento por favor.");
        //progressDialog.setTitle("LuckyTimes Server");

        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btn = (FloatingActionButton) findViewById(R.id.imprimir);

        btn.setVisibility(View.INVISIBLE);

        webView = (WebView) findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webViewPrint = (WebView) findViewById(R.id.webViewPrint);

        webViewPrint.getSettings().setJavaScriptEnabled(true);
        webViewPrint.getSettings().setDomStorageEnabled(true);
        webViewPrint.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        //webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), description, Toast.LENGTH_SHORT).show();
            }

            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                if(url.contains("tiquete") || url.contains("previsualizar")){

                    webViewPrint.loadUrl(url);
                    webView.pageDown(true);
                    webViewPrint.pageDown(true);

                    btn.setVisibility(View.VISIBLE);
                }

                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

        });

        webView.loadUrl(r.direccionURL);

    }

    private int print_size = 4;
    private int error_level = 3;

    public void printLayout(View v){

        View view = findViewById(R.id.webViewPrint);

        String url = webView.getUrl().toString();

        url = url.replace("generar","visualizar");
        url = url.replace("movil", "tiquete");

        printMe.sendViewToPrinter(view);

        if(!url.contains("previsualizar-ganador")){
            printMe.sendQRToPrinter(url,print_size,error_level);
        }

        webView.loadUrl(r.direccionURL);

        btn.setVisibility(View.INVISIBLE);

    }

    public void reloadPage(View v){
        webView.reload();
    }

}