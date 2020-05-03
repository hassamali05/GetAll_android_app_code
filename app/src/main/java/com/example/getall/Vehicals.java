package com.example.getall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Vehicals extends AppCompatActivity {
    private static final String TAG = "Vehicals";
    private ListView listViewV;
   private ArrayList<Item> listCar;
    int j = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicals);

        listViewV = findViewById(R.id.listViewv);
        listCar = new ArrayList<>();

        FetchVeh fetch = new FetchVeh();

        fetch.execute();
        while(j == 0){}
        Custom_AdapterVeh adapter = new Custom_AdapterVeh(Vehicals.this,R.layout.custom_listviewveh,listCar);
        listViewV.setAdapter(adapter);

    }
    private class FetchVeh extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                int i = 0;
                Document docCar = Jsoup.connect("http://www.toyota-indus.com/").userAgent("Mozilla/17.0").get();

                Elements temp2 = docCar.select("div.lcs_logo_container");
                for (Element e : temp2) {

                    String url = "";
                    url = e.select("a img").attr("src");

                    String price = "";
                    price = e.select("span").text();

                    String cut = e.select("div.lcs_logo_title div.slogan").text();
                    String name = "";
                    name = e.select("div.lcs_logo_title").text();
                    name = name.replace(cut, "");
                    cut = e.select("span").text();
                    name = name.replace(cut, "");
                    Item car = new Item(name, price, url);
                    if(i<13) {
                        listCar.add(car);i++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            j = 1;
            return null;
        }
    }
}
