package com.ahmadabuhasan.volleygson;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class UpdateActivity extends AppCompatActivity {

    private ActivityTambahUpdateBinding binding;
    DateFormat dateShow = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    DateFormat dateProcess = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private String et_name, et_address, et_ttl, et_status;
    private int et_id, et_nim;
    String[] status = {"Mahasiswa", "Dosen", "Mata Kuliah"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Update Data");
        binding.saveAddEdit.setText(R.string.update);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            et_id = intent.getInt("et_id");
            et_nim = intent.getInt("et_nim");
            et_name = intent.getString("et_name");
            et_address = intent.getString("et_address");
            et_ttl = intent.getString("et_ttl");
            et_status = intent.getString("et_status");
        }

        binding.nim.setText(String.valueOf(et_nim));
        binding.name.setText(et_name);
        binding.address.setText(et_address);
        binding.ttl.setText(et_ttl);
        binding.status.setText(et_status);

        this.binding.saveAddEdit.setOnClickListener(v -> editData());
        this.binding.ttl.setOnClickListener(view -> showDateDialog());
        this.binding.status.setOnClickListener(v -> statusDialog());
    }

    private void editData() {
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
        assert date != null;
        String ttl = dateProcess.format(date);

        if (nim.isEmpty()) {
            Toasty.warning(UpdateActivity.this, "NIM cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (name.isEmpty()) {
            Toasty.warning(UpdateActivity.this, "Name cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (address.isEmpty()) {
            Toasty.warning(UpdateActivity.this, "Address cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (ttl.isEmpty()) {
            Toasty.warning(UpdateActivity.this, "TTL cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else if (status.isEmpty()) {
            Toasty.warning(UpdateActivity.this, "Status cannot be empty!", Toasty.LENGTH_SHORT, true).show();
        } else {
            updateData(et_id, nim, name, address, ttl, status);
        }
    }

    private void updateData(final int id, final String nim, final String nama, final String alamat, final String ttl, final String status) {
        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/update";
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            ResponseStatus responseStatus = new Gson().fromJson(response, ResponseStatus.class);
            int status_kode = responseStatus.getStatus_code();
            String status_message = responseStatus.getStatus_message();

            if (status_kode == 1) {
                Toasty.success(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT, true).show();
                MainActivity.mInstance.loadData();
                finish();
            } else if (status_kode == 2) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 3) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 4) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 5) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 6) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 7) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 8) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 9) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else if (status_kode == 10) {
                Toasty.normal(UpdateActivity.this, status_message, Toasty.LENGTH_SHORT).show();
            } else {
                Toasty.normal(UpdateActivity.this, "Status Unknown error!", Toasty.LENGTH_SHORT).show();
            }
        }, error -> {
            if (error instanceof TimeoutError) {
                Toasty.error(UpdateActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof NoConnectionError) {
                Toasty.error(UpdateActivity.this, "Network NoConnectionError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof AuthFailureError) {
                Toasty.error(UpdateActivity.this, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof ServerError) {
                Toasty.error(UpdateActivity.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof NetworkError) {
                Toasty.error(UpdateActivity.this, "Network Error", Toasty.LENGTH_SHORT, true).show();
            } else if (error instanceof ParseError) {
                Toasty.error(UpdateActivity.this, "Parse Error", Toasty.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(UpdateActivity.this, "Unknown Error Status!", Toasty.LENGTH_SHORT, true).show();
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("id", String.valueOf(id));
                hashMap.put("nim", nim);
                hashMap.put("nama", nama);
                hashMap.put("alamat", alamat);
                hashMap.put("date", ttl);
                hashMap.put("status", status);
                return hashMap;
            }
        };

        AppController.getInstance().addToQueue(request, "edit_data");
    }

    private void showDateDialog() {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DateEdit, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            binding.ttl.setText(dateShow.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void statusDialog() {
        View dialogView = UpdateActivity.this.getLayoutInflater().inflate(R.layout.status_dialog, null);
        final AlertDialog.Builder dialogStatus = new AlertDialog.Builder(this);

        dialogStatus.setView(dialogView);
        dialogStatus.setTitle("---Select---");
        dialogStatus.setCancelable(false);

        dialogStatus.setItems(status, (dialog, which) -> {
            if ("Mahasiswa".equals(status[which])) {
                binding.status.setText(R.string.student);
                Toasty.success(UpdateActivity.this, "Mahasiswa", Toasty.LENGTH_SHORT, true).show();
            } else if ("Dosen".equals(status[which])) {
                binding.status.setText(R.string.lecturer);
                Toasty.success(UpdateActivity.this, "Dosen", Toasty.LENGTH_SHORT, true).show();
            } else if ("Mata Kuliah".equals(status[which])) {
                binding.status.setText(R.string.subject);
                Toasty.success(UpdateActivity.this, "Mata Kuliah", Toasty.LENGTH_SHORT, true).show();
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