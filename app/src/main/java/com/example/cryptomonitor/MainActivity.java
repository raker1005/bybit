package com.example.cryptomonitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new NativeHttpBridge(), "AndroidBridge");
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    public static class NativeHttpBridge {
        @JavascriptInterface
        public String get(String urlString) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(urlString);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 Android CryptoMonitor");
                conn.setRequestProperty("Accept", "application/json,text/plain,*/*");
                int code = conn.getResponseCode();
                InputStream is = code >= 200 && code < 300 ? conn.getInputStream() : conn.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();
                if (code < 200 || code >= 300) {
                    return "{\"__nativeError\":\"HTTP " + code + "\",\"body\":" + quote(sb.toString()) + "}";
                }
                return sb.toString();
            } catch (Exception e) {
                return "{\"__nativeError\":" + quote(e.getMessage() == null ? e.toString() : e.getMessage()) + "}";
            } finally {
                if (conn != null) conn.disconnect();
            }
        }

        private static String quote(String s) {
            if (s == null) return "\"\"";
            StringBuilder out = new StringBuilder("\"");
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '\\': out.append("\\\\"); break;
                    case '"': out.append("\\\""); break;
                    case '\n': out.append("\\n"); break;
                    case '\r': out.append("\\r"); break;
                    case '\t': out.append("\\t"); break;
                    default:
                        if (c < 32) out.append(String.format("\\u%04x", (int)c));
                        else out.append(c);
                }
            }
            out.append("\"");
            return out.toString();
        }
    }
}
