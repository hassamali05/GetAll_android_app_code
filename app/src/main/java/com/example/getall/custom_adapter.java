package com.example.getall;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class custom_adapter extends ArrayAdapter<Item> {

    Context context;
    int resource;
    ArrayList<Item> list;
    public custom_adapter(Context context, int resource, ArrayList<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        list = objects;
    }

    private static class MyItems{
        ImageView image;
        TextView name;
        TextView price;
        Button Save;
        Button Share;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyItems holder = null;
        if(row == null){
            LayoutInflater inflater = ((MainActivity) context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new MyItems();
            holder.image = row.findViewById(R.id.imageView);
            holder.name = row.findViewById(R.id.name);
            holder.price = row.findViewById(R.id.price);
            holder.Save = row.findViewById(R.id.save);
            holder.Share = row.findViewById(R.id.share);
            row.setTag(holder);
        }
        else{
            holder = (MyItems) row.getTag();
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        width -= 30;
        width = (int) width/2;

        holder.name.setWidth(width);
        holder.price.setWidth(width);
        holder.Share.setWidth(width);
        holder.Save.setWidth(width);


        final Item i = list.get(position);
        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                text = i.getUrl()+"\n"+i.getName()+i.getPrice();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");// Plain format text

                // You can add subject also
                /*
                 * sharingIntent.putExtra( android.content.Intent.EXTRA_SUBJECT,
                 * "Subject Here");
                 */
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
                context.startActivity(Intent.createChooser(sharingIntent,"Share Using"));
            }
        });
        holder.name.setText(i.getName());
        holder.price.setText(i.getPrice());
        Picasso.get().load(i.getUrl()).into(holder.image);
        return row;
    }
}
