package com.example.rohankandwal.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private ProgressDialog progDailog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    final EditText etURL = findViewById(R.id.etURL);
    final WebView webView = findViewById(R.id.webView);

    progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
    progDailog.setCancelable(false);

    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setLoadWithOverviewMode(true);
    webView.getSettings().setUseWideViewPort(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new WebViewClient() {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        progDailog.show();
        view.loadUrl(url);

        return true;
      }

      @Override
      public void onPageFinished(WebView view, final String url) {
        progDailog.dismiss();
      }

      @Override
      public void onReceivedError(
        WebView view, WebResourceRequest request, WebResourceError error
      ) {
        progDailog.dismiss();
        Toast.makeText(MainActivity.this, error.getDescription(), Toast.LENGTH_SHORT).show();
        super.onReceivedError(view, request, error);
      }
    });
    //Load url in webview
    findViewById(R.id.bLoad).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        webView.loadUrl(etURL.getText().toString().trim());
      }
    });
    webView.loadUrl(etURL.getText().toString().trim());
  }
}
