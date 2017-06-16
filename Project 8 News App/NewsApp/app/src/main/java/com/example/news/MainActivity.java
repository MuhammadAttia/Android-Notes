package com.example.news;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    public static final String TARGET_URL =
            "http://content.guardianapis.com/search?tag=technology%2Fgames&from-date=2016-01-01&order-by=newest&page-size=20&show-fields=thumbnail%2Cbyline%2CtrailText&api-key=test";
    private ArrayList newsArrayList;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsArrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new getNews().execute();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener
                .OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //handle click events here
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                News news =(News)newsArrayList.get(itemPosition);
                Uri link = Uri.parse(news.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW,link);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //handle longClick if any
            }
        }));

    }

    private class getNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler httpHandler = new HttpHandler();
            String jsonStr = httpHandler.makeServiceCall(TARGET_URL);
            if (jsonStr != null) {
                try {
                    JSONObject main = new JSONObject(jsonStr);
                    JSONObject response = main.getJSONObject("response");
                    JSONArray results = response.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject newsObject = results.getJSONObject(i);
                        JSONObject fields = newsObject.getJSONObject("fields");
                        String title = newsObject.getString("webTitle");
                        String author = fields.optString("byline");
                        String trail = fields.optString("trailText");
                        String date = newsObject.getString("webPublicationDate");
                        String section = newsObject.getString("sectionName");
                        String url = newsObject.getString("webUrl");

                        newsArrayList.add(new News(title, author, trail, date, section, url));

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAdapter = new NewsAdapter(getApplicationContext(), newsArrayList);
            recyclerView.setAdapter(mAdapter);
        }

    }

}

