package com.ahmadabuhasan.volleygson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by Ahmad Abu Hasan on 14/12/2020
 * "com.android.tools.build:gradle:4.0.1"
 */

public class MainActivity extends AppCompatActivity {

    // implementasi
    private RecyclerView recyclerView;
    private FloatingActionButton fab_tambah;

    private RecyclerViewAdapter adapter;
    private ArrayList<ModelBarang> arrayModelBarangs;

    public static MainActivity mInstance;

    private static long back_pressed;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mendeklarasi
        recyclerView = findViewById(R.id.recyclerview);
        fab_tambah = findViewById(R.id.fab_tambah_barang);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toasty.info(MainActivity.this, "Data UpToDate", Toasty.LENGTH_SHORT, true).show();
                MuatData();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 2000);
            }
        });

        // agar method mainActivity bisa diakses
        mInstance = this;

        // membuat linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // menset ke recyclerview layoutmanger
        recyclerView.setLayoutManager(linearLayoutManager);

        // memberi event klik pada fab (floating action buttom)
        fab_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // berpindah ke class TambahActivity
                startActivity(new Intent(MainActivity.this, TambahActivity.class));
            }
        });

        // untuk menghemat ruang kita buat method MuadData
        MuatData();
    }

    // method MuadData kita set ke public
    public void MuatData() {
//        String url = "https://subkode.000webhostapp.com/volley_db/lihat_barang";
        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/read";
        // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // dikarenakan hasil json diawali dengan array maka membuat type
                    Type typeModelBarang = new TypeToken<ArrayList<ModelBarang>>() {
                    }.getType();
                    // menerapkan ke model class menggunakan GSON
                    // mengkonversi JSON ke java object
                    arrayModelBarangs = new Gson().fromJson(response, typeModelBarang);

                    // memanggil kontruktor adapter dan mengimplementasikannya
                    adapter = new RecyclerViewAdapter(MainActivity.this, arrayModelBarangs);
                    // lalu menset ke recyclerview adapter
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toasty.normal(MainActivity.this, "Data Kosong!", Toasty.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika data tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toasty.error(MainActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NoConnectionError) {
                    Toasty.error(MainActivity.this, "Nerwork NoConnectionError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(MainActivity.this, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(MainActivity.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(MainActivity.this, "Network Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(MainActivity.this, "Parse Error", Toasty.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(MainActivity.this, "Status Kesalahan Tidak Diketahui!", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

        // memanggil AppController dan menambahkan dalam antrin
        // text "data" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "data");
    }

    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toasty.info(MainActivity.this, R.string.press_once_again_to_exit, Toasty.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.code) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://github.com/eby8zevin/android-VolleyGson"));
            startActivity(i);
        }
        return true;
    }

}