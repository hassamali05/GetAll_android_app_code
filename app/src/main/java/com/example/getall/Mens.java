package com.example.getall;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Mens extends AppCompatActivity {

    ListView listView;
   private ArrayList<Item> listMen;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mens);

        listView = findViewById(R.id.listViewm);
        listMen = new ArrayList<>();

        Fetch fetch = new Fetch();
        fetch.execute();
        while(j == 0){}
        Custom_AdapterMen adapter = new Custom_AdapterMen(Mens.this,R.layout.custom_listviewmen,listMen);
        listView.setAdapter(adapter);


    }

    private class Fetch extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<String> image = new ArrayList<String>();
            ArrayList<String> name = new ArrayList<String>();
            ArrayList<String> price = new ArrayList<>();

            Document docw = null;
            try {
                docw = Jsoup.connect("https://www.watchshop.com/mens-watches.html").userAgent("Mozilla/17.0").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements prices = docw.select("div.price.price-listing-page div.d-inline");
            Elements names = docw.select("div.listing-product-name p.text-truncate");
            Elements images = docw.select("div.listing-product-image");


            for (Element e : images) {
                String temp1 = e.select("img.img-fluid").attr("data-img-src");
                image.add(temp1);
            }
            for (Element e : names) {
                String temp1 = e.select("p").attr("title").toString();
                name.add(temp1);
            }
            for (Element e : prices) {
                String temp1 = "£" + e.attr("content");
                price.add(temp1);
            }
            for (int i = 0; i < price.size(); i++) {
                String temp1 = image.get(i);
                if (temp1.equals(""))
                    continue;
                else {
                    Item w = new Item(name.get(i), price.get(i), "https:" + temp1);
                    listMen.add(w);
                }
            }


            j = 1;
            return null;
        }

    }
}
