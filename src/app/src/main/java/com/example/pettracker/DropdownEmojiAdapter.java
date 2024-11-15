package com.example.pettracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DropdownEmojiAdapter extends ArrayAdapter<DropdownEmoji> {

    public DropdownEmojiAdapter(Context context, List<DropdownEmoji> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }

        DropdownEmoji currentItem = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.si_picture_Standard); // Use the appropriate ID
        TextView textView = convertView.findViewById(R.id.si_text_Standard);

        imageView.setImageResource(currentItem.getImageResId());
        textView.setText(currentItem.getText());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

