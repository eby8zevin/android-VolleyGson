package com.ahmadabuhasan.volleygson;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmadabuhasan.volleygson.databinding.ActivityTambahUpdateBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class AddActivity extends AppCompatActivity {

    private ActivityTambahUpdateBinding binding;
    DateFormat dateShow = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    DateFormat dateProcess = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    String[] status = {"Mahasiswa", "Dosen", "Mata Kuliah"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Add Data");

        this.binding.saveAddEdit.setOnClickListener(v -> updateData());
        this.binding.ttl.setOnClickListener(view -> showDateDialog());
        this.binding.status.setOnClickListener(v -> statusDialog());
    }

    private void updateData() {
        String nim = Objects.requireNonNull(binding.nim.getText()).toString();
        String name = Objects.requireNonNull(binding.name.getText()).toString();
        String address = Objects.requireNonNull(binding.address.getText()).toString();
        String status = Objects.requireNonNull(binding.status.getText()).toString();

        String ttl_input = Objects.requireNonNull(binding.ttl.getText()).toString();
        Date date = null;
        try {
            date = dateShow.parse(ttl_input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String ttl = null;
        if (date != null) {
            ttl = dateProcess.format(date);
        }

        if (nim.isEmpty()) {
            Toasty.warning(AddActivity.this, "NIM cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (name.isEmpty()) {
            Toasty.warning(AddActivity.this, "Name cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (address.isEmpty()) {
            Toasty.warning(AddActivity.this, "Address cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (ttl == null) {
            Toasty.warning(AddActivity.this, "TTL cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (status.isEmpty()) {
            Toasty.warning(AddActivity.this, "Status cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else {
            try {
                saveData(nim, name, address, ttl, status);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveData(String nim, String name, String address, String ttl, String status) throws UnsupportedEncodingException {
        String conv_name = URLEncoder.encode(name, "utf-8");
        String conv_address = URLEncoder.encode(address, "utf-8");
        String conv_ttl = URLEncoder.encode(ttl, "utf-8");
        String conv_status = URLEncoder.encode(status, "utf-8");

        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/create?nim=" + nim + "&nama=" + conv_name + "&alamat=" + conv_address + "&date=" + conv_ttl + "&status=" + conv_status;
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            ResponseStatus responStatus = new Gson().fromJson(response, ResponseStatus.class);
            int status_kode = responStatus.getStatus_code();
            String status_message = responStatus.getStatus_message();

            if (status_kode == 1) {
                Toasty.success(AddActivity.this, status_message, Toasty.LENGTH_SHORT, true).show();
                MainActivity.mInstance.loadData();
                finish();
            } else if (status_kode == 2) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 3) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 4) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 5) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 6) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 7) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 8) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 9) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 10) {
                Toasty.normal(AddActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else {
                Toasty.normal(AddActivity.this, "Status Unknown error!", Toasty.LENGTH_SHORT).show();
            }
        }, error -> {
            if (error instanceof TimeoutError) {
                Toasty.error(AddActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof NoConnectionError) {
                Toasty.error(AddActivity.this, "Network NoConnectionError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof AuthFailureError) {
                Toasty.error(AddActivity.this, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof ServerError) {
                Toasty.error(AddActivity.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof NetworkError) {
                Toasty.error(AddActivity.this, "Network Error", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof ParseError) {
                Toasty.error(AddActivity.this, "Parse Error", Toasty.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(AddActivity.this, "Status Error Tidak Diketahui!", Toasty.LENGTH_SHORT, true).show();
            }
        });

        AppController.getInstance().addToQueue(request, "add_data");
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            binding.ttl.setText(dateShow.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void statusDialog() {
        View dialogView = AddActivity.this.getLayoutInflater().inflate(R.layout.status_dialog, null);
        final AlertDialog.Builder dialogStatus = new AlertDialog.Builder(this);

        dialogStatus.setView(dialogView);
        dialogStatus.setTitle("---Select---");
        dialogStatus.setCancelable(false);

        dialogStatus.setItems(status, (dialog, which) -> {
            if ("Mahasiswa".equals(status[which])) {
                binding.status.setText(R.string.student);
                Toasty.success(AddActivity.this, "Mahasiswa", Toasty.LENGTH_SHORT, true).show();
            } else if ("Dosen".equals(status[which])) {
                binding.status.setText(R.string.lecturer);
                Toasty.success(AddActivity.this, "Dosen", Toasty.LENGTH_SHORT, true).show();
            } else if ("Mata Kuliah".equals(status[which])) {
                binding.status.setText(R.string.subject);
                Toasty.success(AddActivity.this, "Mata Kuliah", Toasty.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(this, "Error!", Toasty.LENGTH_SHORT, true).show();
            }
        });

        final AlertDialog alertDialog = dialogStatus.create();
        dialogView.findViewById(R.id.dialog_button).setOnClickListener(v -> alertDialog.dismiss());
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}