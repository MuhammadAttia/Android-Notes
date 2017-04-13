//first add volly library 
    compile 'com.mcxiaoke.volley:library:1.0.19'
 


package com.example.news;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    public static final String TARGET_URL =
            "http://content.guardianapis.com/search?tag=technology%2Fgames&from-date=2016-01-01&order-by=newest&page-size=20&show-fields=thumbnail%2Cbyline%2CtrailText&api-key=test";
    private ArrayList newsArrayList;
    private RecyclerView recyclerView;
    private NewsAdapter mAdapter;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        requestQueue = Volley.newRequestQueue(this);
        newsArrayList = new ArrayList<>();
        FetchJsonResponse(TARGET_URL);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener
                .OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                //handle click events here
                int itemPosition = recyclerView.getChildLayoutPosition(view);
                News news = (News) newsArrayList.get(itemPosition);
                Uri link = Uri.parse(news.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, link);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                //handle longClick if any
            }
        }));

    }
    private void FetchJsonResponse(String url) {


        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main = response.getJSONObject("response");
                            JSONArray results = main.getJSONArray("results");

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
                            mAdapter = new NewsAdapter(getApplicationContext(), newsArrayList);
                            recyclerView.setAdapter(mAdapter);
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        // Adds the JSON object request "req" to the request queue
        requestQueue.add(req);

    }

}

