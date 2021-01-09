package com.ahmadabuhasan.volleygson;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

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

/*
 * Created by Ahmad Abu Hasan on 14/12/2020
 * Update "dd-MM-yyyy convert yyyy-MM-dd on 08/01/2021"
 */

public class TambahActivity extends AppCompatActivity {

    // implementasi
    private EditText nim_data, nama_data, alamat_data, ttl_data, status_data;

    private DateFormat dateShow, dateProcess;

    String[] status = {"Mahasiswa", "Dosen", "Mata Kuliah"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // deklarasi
        nim_data = findViewById(R.id.nim);
        nama_data = findViewById(R.id.nama);
        alamat_data = findViewById(R.id.alamat);
        ttl_data = findViewById(R.id.ttl);
        status_data = findViewById(R.id.status);
        Button btn_simpan = findViewById(R.id.simpan_tambah_ubah);

        // memberi action pada floating action buttom
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nim = nim_data.getText().toString();
                String nama = nama_data.getText().toString();
                String alamat = alamat_data.getText().toString();
                //String ttl = ttl_data.getText().toString();
                String status = status_data.getText().toString();

                String ttl_input = ttl_data.getText().toString();
                dateProcess = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

                // validasi kode, nama dan harga tidak boleh kosong
                if (nim.isEmpty()) {
                    Toasty.warning(TambahActivity.this, "NIM Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (nama.isEmpty()) {
                    Toasty.warning(TambahActivity.this, "Nama Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (alamat.isEmpty()) {
                    Toasty.warning(TambahActivity.this, "Alamat Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (ttl == null) {
                    Toasty.warning(TambahActivity.this, "TTL Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (status.isEmpty()) {
                    Toasty.warning(TambahActivity.this, "Status Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else {
                    // menyimpan data ke database
                    try {
                        simpanData(nim, nama, alamat, ttl, status);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        /*
         * Kita menggunakan format tanggal dd-MM-yyyy
         * jadi nanti tanggal nya akan diformat menjadi
         * misalnya 31-12-2020
         */
        dateShow = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        this.ttl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });

        this.status_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusDialog();
            }
        });
    }

    private void simpanData(String nim, String nama, String alamat, String ttl, String status) throws UnsupportedEncodingException {
        // karena text ini kita masukkan ke-link maka kode dan nama kita konversikan ke bentuk "utf-8"
        // contoh : "Samsung Galaxy M2" ==> dikonversi menjadi "Samsung+Galaxy+M2"
        // dan ketika di simpan ke database text tidak akan berupa text konversi melaikan text aslinya.
        String konv_nama = URLEncoder.encode(nama, "utf-8");
        String konv_alamat = URLEncoder.encode(alamat, "utf-8");
        String konv_ttl = URLEncoder.encode(ttl, "utf-8");
        String konv_status = URLEncoder.encode(status, "utf-8");

        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/create?nim=" + nim + "&nama=" + konv_nama + "&alamat=" + konv_alamat + "&date=" + konv_ttl + "&status=" + konv_status;
        // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // menerapkan ke model class menggunakan GSON
                // mengkonversi JSON ke java object
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();

                // jika status kode sama dengan 1 maka berhasil
                if (status_kode == 1) {
                    Toasty.success(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT, true).show();
                    MainActivity.mInstance.MuatData(); // memanggil MainActivity untuk memproses method MemuatData()
                    finish(); // keluar/selesai
                } else if (status_kode == 2) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toasty.normal(TambahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.normal(TambahActivity.this, "Status Kesalahan Tidak Diketahui!", Toasty.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toasty.error(TambahActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NoConnectionError) {
                    Toasty.error(TambahActivity.this, "Nerwork NoConnectionError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(TambahActivity.this, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(TambahActivity.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(TambahActivity.this, "Network Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(TambahActivity.this, "Parse Error", Toasty.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(TambahActivity.this, "Status Error Tidak Diketahui!", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

        // memanggil AppController dan menambahkan dalam antrin
        // text "add_data" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "add_data");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDateDialog() {

        //Calendar untuk mendapatkan tanggal sekarang
        Calendar newCalendar = Calendar.getInstance();

        /*
         * Initiate DatePicker dialog
         * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
         * Set Calendar untuk menampung tanggal yang dipilih
         * Update EditText dengan tanggal yang kita pilih
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /*
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                //Update EditText dengan tanggal yang kita pilih
                ttl_data.setText(dateShow.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        //Tampilkan DatePicker dialog
        datePickerDialog.show();
    }

    @SuppressLint("ResourceType")
    private void statusDialog() {
        View dialogView = TambahActivity.this.getLayoutInflater().inflate(R.layout.status_dialog, null);
        final AlertDialog.Builder dialogStatus = new AlertDialog.Builder(this);

        dialogStatus.setView(dialogView);
        dialogStatus.setTitle("---Select---");
        dialogStatus.setCancelable(false);

        dialogStatus.setItems(status, new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if ("Mahasiswa".equals(status[which])) {
                    status_data.setText("Mahasiswa");
                    Toasty.success(TambahActivity.this, "Mahasiswa", Toasty.LENGTH_SHORT, true).show();
                } else if ("Dosen".equals(status[which])) {
                    status_data.setText("Dosen");
                    Toasty.success(TambahActivity.this, "Dosen", Toasty.LENGTH_SHORT, true).show();
                } else if ("Mata Kuliah".equals(status[which])) {
                    status_data.setText("Mata Kuliah");
                    Toasty.success(TambahActivity.this, "Mata Kuliah", Toasty.LENGTH_SHORT, true).show();
                }
            }
        });

        final AlertDialog alertDialog = dialogStatus.create();
        dialogView.findViewById(R.id.dialog_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}