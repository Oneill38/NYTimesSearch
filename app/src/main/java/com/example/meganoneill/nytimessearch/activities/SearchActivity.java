package com.example.meganoneill.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.meganoneill.nytimessearch.ArticleArrayAdapter;
import com.example.meganoneill.nytimessearch.R;
import com.example.meganoneill.nytimessearch.fragments.FilterFragment;
import com.example.meganoneill.nytimessearch.models.Article;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class SearchActivity extends AppCompatActivity implements FilterFragment.EditNameDialogListener{
    GridView gvResults;

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;

    //values from fragment
    String date;
    String sort;
    Boolean sports = false;
    Boolean arts = false;
    Boolean fashion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViews();
    }

    @Override
    public void onFinishEditDialog(String the_date, String the_sort, Boolean bool_sports, Boolean bool_arts, Boolean bool_fashion) {
        //Set options query string here?
        date = the_date;
        sort = the_sort;
        sports = bool_sports;
        arts = bool_arts;
        fashion = bool_fashion;

    }


    private void showFilterFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment searchFilterDialog = new FilterFragment();
        searchFilterDialog.show(fm, "filter_fragment");
    }

    public void setupViews(){
        gvResults = (GridView) findViewById(R.id.gvResults);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);

                Article article = articles.get(position);

                i.putExtra("article", article);

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                articleSearch(query);
                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used
                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.btnFilter){
            showFilterFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void articleSearch(String query) {
        ArrayList<String> subjects = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "69a1ad42e13c41f0a00cb881300e3ecd");
        params.put("page", 0);
        params.put("q", query);

        if (!TextUtils.isBlank(sort)) {
            params.put("sort", sort);
        }

        if (!TextUtils.isBlank(date)) {
            ParsePosition pos = new ParsePosition(0);
            Date new_date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(date, pos);
            params.put("begin_date", new_date);
        }

        if (fashion == true){
            subjects.add("Fashion & Style");
        }

        if(sports == true){
            subjects.add("Sports");
        }

        if(arts == true){
            subjects.add("Arts");
        }

        if(subjects.size() > 0){
            params.put("fq", "news_desk:(\"" + android.text.TextUtils.join("\" \"", subjects.toArray()) + "\")");
        }

        Log.d("DEBUG", params.toString());

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;

                try{
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    Log.d("DEBUG", articleJsonResults.toString());
                    adapter.addAll(Article.fromJSONArray(articleJsonResults));
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

}
