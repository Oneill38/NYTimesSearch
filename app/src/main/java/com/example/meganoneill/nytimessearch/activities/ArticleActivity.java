package com.example.meganoneill.nytimessearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.meganoneill.nytimessearch.R;
import com.example.meganoneill.nytimessearch.models.Article;

import org.parceler.Parcels;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));

        WebView webview = (WebView) findViewById(R.id.wvArticle);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                view.loadUrl(article.getWebUrl());
                return true;
            }
        });

        webview.loadUrl(article.getWebUrl());
    }

}
