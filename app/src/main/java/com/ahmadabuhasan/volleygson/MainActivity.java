package com.ahmadabuhasan.volleygson;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmadabuhasan.volleygson.databinding.ActivityMainBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public static MainActivity mInstance;

    private RecyclerViewAdapter adapter;
    private ArrayList<ModelBarang> arrayModelData;

    private static long back_pressed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mInstance = this;

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary, R.color.black);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.fabAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddActivity.class)));

        swipeRefresh();
        loadData();
    }

    public void loadData() {
        binding.swipeRefresh.setRefreshing(false);
        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/read";
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            try {
                Type typeModelData = new TypeToken<ArrayList<ModelBarang>>() {
                }.getType();
                arrayModelData = new Gson().fromJson(response, typeModelData);

                adapter = new RecyclerViewAdapter(MainActivity.this, arrayModelData);
                binding.recyclerView.setAdapter(adapter);
                binding.tvConnect.setText(R.string.connect_server);
            } catch (Exception e) {
                Toasty.normal(MainActivity.this, "Data not found!", Toasty.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> {
            if (error instanceof TimeoutError) {
                Toasty.error(MainActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof NoConnectionError) {
                Toasty.error(MainActivity.this, "Network NoConnectionError", Toasty.LENGTH_SHORT, true).show();
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
        });

        AppController.getInstance().addToQueue(request, "data");
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setQueryHint("Search Name...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter != null) {
                    adapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.api) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://blackpink-marketplace.000webhostapp.com/tugas/api/read"));
            startActivity(i);
        } else if (item.getItemId() == R.id.code) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://github.com/eby8zevin/android-VolleyGson"));
            startActivity(i);
        } else if (item.getItemId() == R.id.refresh) {
            binding.swipeRefresh.setRefreshing(true);
            loadData();
        }
        return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null;
    }

    private void swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> new Handler().postDelayed(() -> {
            binding.swipeRefresh.setRefreshing(false);
            boolean connection = isNetworkAvailable();
            if (connection) {
                Toasty.info(MainActivity.this, "Data UpToDate", Toasty.LENGTH_SHORT, true).show();
                loadData();
            } else {
                binding.tvConnect.setText(R.string.not_connected);
                adapter.clear();
            }
        }, 3000));
    }

    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toasty.info(MainActivity.this, R.string.press_once_again_to_exit, Toasty.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}