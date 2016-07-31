package com.example.meganoneill.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_article, menu);

        MenuItem item = menu.findItem(R.id.menu_item_share);
        ShareActionProvider miShare = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        // get reference to WebView
        WebView wvArticle = (WebView) findViewById(R.id.wvArticle);
        // pass in the URL currently being used by the WebView
        shareIntent.putExtra(Intent.EXTRA_TEXT, wvArticle.getUrl());

        miShare.setShareIntent(shareIntent);
        return super.onCreateOptionsMenu(menu);
    }

}
