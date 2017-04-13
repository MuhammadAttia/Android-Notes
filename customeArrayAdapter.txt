package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Attia on 25/02/2017.
 */

public class CustomArrayAdapter extends ArrayAdapter <Word> {

    public CustomArrayAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Word words = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        // Lookup view for data population
        TextView english = (TextView) convertView.findViewById(R.id.defult);
        TextView mikow = (TextView) convertView.findViewById(R.id.mikow);
        // Populate the data into the template view using the data object
        english.setText(words.getDefaultTranslation());
        mikow.setText(words.getMiwokTranslation());
        // Return the completed view to render on screen
        return convertView;
    }
}
