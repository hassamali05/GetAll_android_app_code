package com.example.getall;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayList<Item> list;
    int j = 0;
    int w=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hassam
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();
 
        Fetch fetch = new Fetch();
        fetch.execute();
        while(j == 0){}
        custom_adapter adapter = new custom_adapter(MainActivity.this,R.layout.custom_listview,list);
        listView.setAdapter(adapter);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //Hassam function
    private class Fetch extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            ArrayList<String> image = new ArrayList<String>();
            ArrayList<String> namew = new ArrayList<String>();
            ArrayList<String> pricew = new ArrayList<>();


            try {
                Document doc = Jsoup.connect("http://www.toyota-indus.com/").userAgent("Mozilla/17.0").get();
                Elements temp = doc.select("div.lcs_logo_container");
                for (Element e : temp) {

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
                    list.add(car);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Document docw = Jsoup.connect("https://www.watchshop.com/mens-watches.html").userAgent("Mozilla/17.0").get();
                Elements prices = docw.select("div.price.price-listing-page div.d-inline");
                Elements names = docw.select("div.listing-product-name p.text-truncate");
                Elements images = docw.select("div.listing-product-image");


                for(Element e:images)
                {
                    String temp = e.select("img.img-fluid").attr("data-img-src");
                    image.add(temp);
                }
                for(Element e:names){
                    String temp = e.select("p").attr("title").toString();
                    namew.add(temp);
                }
                for(Element e:prices){
                    String temp = "£"+e.attr("content");
                    pricew.add(temp);
                }
                for(int i = 0;i<pricew.size();i++){
                    String temp = image.get(i);
                    if(temp.equals(""))
                        continue;
                    else{
                        Item w = new Item(namew.get(i),pricew.get(i),"https:"+temp);
                        list.add(w);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            j = 1;
            return null;
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else if (id == R.id.cat) {
            Intent i = new Intent(this,Catagories.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            Intent i = new Intent(this,About_Us.class);
            startActivity(i);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
