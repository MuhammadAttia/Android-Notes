package com.example.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<News> mNewsList = new ArrayList<>();
    private Context mContext;

    public NewsAdapter(Context mContext,ArrayList<News> mNewsList) {
        this.mContext = mContext;
        this.mNewsList = mNewsList;

    }
    private Context getContext() {
        return mContext;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        // Inflate the custom layout
        View view = LayoutInflater.from(context).inflate(R.layout.single_news, parent, false);
        // Return a new holder instance
        NewsViewHolder holder = new NewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {

        holder.titleText.setText(mNewsList.get(position).getTitle());
        holder.sectionText.setText(mNewsList.get(position).getSection());

        TextView authorText = holder.authorText;
        if (mNewsList.get(position).getAuthor().equals("")) {
            authorText.setText(R.string.no_author);
        } else {
            authorText.setText(mNewsList.get(position).getAuthor());
        }

        holder.dateText.setText(formatDate(mNewsList.get(position).getDate()));
        holder.trailText.setText(mNewsList.get(position).getTrailText());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        public TextView titleText;
        public TextView sectionText;
        public TextView authorText;
        public TextView dateText;
        public TextView trailText;

        public NewsViewHolder(View itemView) {
            super(itemView);

            titleText = (TextView)itemView.findViewById(R.id.title_text);
            sectionText = (TextView) itemView.findViewById(R.id.section_text);
            authorText = (TextView) itemView.findViewById(R.id.author_text);
            dateText = (TextView) itemView.findViewById(R.id.date_text);
            trailText = (TextView) itemView.findViewById(R.id.trail_text);


        }

    }

    private String formatDate(String date) {

        SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat dateOutput = new SimpleDateFormat("LLL dd, yyyy - h:mm a");
        String dateReturn = null;
        try {
            dateReturn = dateOutput.format(dateInput.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateReturn;
    }

}

