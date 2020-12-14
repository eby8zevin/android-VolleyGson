package com.ahmadabuhasan.volleygson;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

/*
 * Created by Ahmad Abu Hasan on 14/12/2020
 */

public class UbahActivity extends AppCompatActivity {

    // implementasi
//    private EditText kode_barang, nama_barang, harga_barang;

    private EditText nim_data, nama_data, alamat_data, ttl_data, status_data;
    // untuk menerima Data dari MainActivity
//    private String ed_kode, ed_nama;
//    private int ed_id, ed_harga;

    private String et_nama, et_alamat, et_ttl, et_status;
    private int et_id, et_nim;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    String[] status = {"Mahasiswa", "Dosen", "Mata Kuliah"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // deklarasi
//        kode_barang = findViewById(R.id.ed_kode_barang);
//        nama_barang = findViewById(R.id.ed_nama_barang);
//        harga_barang = findViewById(R.id.ed_harga_barang);

        nim_data = findViewById(R.id.nim);
        nama_data = findViewById(R.id.nama);
        alamat_data = findViewById(R.id.alamat);
        ttl_data = findViewById(R.id.ttl);
        status_data = findViewById(R.id.status);
        Button btn_simpan = findViewById(R.id.simpan_tambah_ubah);

        // mengubah text pada buttom
        btn_simpan.setText("Update");

        // menerima data dari MainActivity menggunakana "Bundle"
        Bundle intent = getIntent().getExtras();
        if (intent != null) {
//            ed_id = intent.getInt("ed_id");
//            ed_kode = intent.getString("ed_kode");
//            ed_nama = intent.getString("ed_nama");
//            ed_harga = intent.getInt("ed_harga");

            et_id = intent.getInt("et_id");
            et_nim = intent.getInt("et_nim");
            et_nama = intent.getString("et_nama");
            et_alamat = intent.getString("et_alamat");
            et_ttl = intent.getString("et_ttl");
            et_status = intent.getString("et_status");
        }

        // lalu "Bundle" ini, akan di set ke edittext
//        kode_barang.setText(ed_kode);
//        nama_barang.setText(ed_nama);
//        harga_barang.setText(String.valueOf(ed_harga));

        nim_data.setText(String.valueOf(et_nim));
        nama_data.setText(et_nama);
        alamat_data.setText(et_alamat);
        ttl_data.setText(et_ttl);
        status_data.setText(et_status);

        // memberi action pada floating action buttom
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mengambil text dalam edittext
//                String kode = kode_barang.getText().toString();
//                String nama = nama_barang.getText().toString();
//                String harga = harga_barang.getText().toString();

                String nim = nim_data.getText().toString();
                String nama = nama_data.getText().toString();
                String alamat = alamat_data.getText().toString();
                String ttl = ttl_data.getText().toString();
                String status = status_data.getText().toString();

                // validasi kode, nama dan harga tidak boleh kosong
//                if (kode.isEmpty()) { // kode_barang tidak lebih dari 6 digit
//                    Toast.makeText(UbahActivity.this, "Kode Masih Kosong!", Toast.LENGTH_SHORT).show();
//                } else if (nama.isEmpty()) {
//                    Toast.makeText(UbahActivity.this, "Nama Masih Kosong!", Toast.LENGTH_SHORT).show();
//                } else if (harga.isEmpty()) { // harga barang tidak boleh dari 9 digit
//                    Toast.makeText(UbahActivity.this, "Harga Masih Kosong!", Toast.LENGTH_SHORT).show();
//                } else {
//                    // mengupdate data
//                    updateData(ed_id, kode, nama, harga);
//                }

                if (nim.isEmpty()) {
                    Toasty.warning(UbahActivity.this, "NIM Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (nama.isEmpty()) {
                    Toasty.warning(UbahActivity.this, "Nama Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (alamat.isEmpty()) {
                    Toasty.warning(UbahActivity.this, "Alamat Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (ttl.isEmpty()) {
                    Toasty.warning(UbahActivity.this, "TTL Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else if (status.isEmpty()) {
                    Toasty.warning(UbahActivity.this, "Status Masih Kosong!", Toasty.LENGTH_SHORT, true).show();
                } else {
                    // mengupdate data
                    updateData(et_id, nim, nama, alamat, ttl, status);
                }
            }
        });

        /*
         * Kita menggunakan format tanggal dd-MM-yyyy
         * jadi nanti tanggal nya akan diformat menjadi
         * misalnya 31-12-2020
         */
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

//    private void updateData(final int id, final String kode, final String nama, final String harga) {
//        String url = "https://subkode.000webhostapp.com/volley_db/update_barang";

    private void updateData(final int id, final String nim, final String nama, final String alamat, final String ttl, final String status) {
        String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/update";
        // buat StringRequest volley dan jangan lupa requestnya POST "Request.Method.POST"
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // menerapkan ke model class menggunakan GSON
                // mengkonversi JSON ke java object
                ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                int status_kode = responStatus.getStatus_kode();
                String status_pesan = responStatus.getStatus_pesan();

                // jika respon status kode yg dihasilkan 1 maka berhasil
                if (status_kode == 1) {
                    Toasty.success(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT, true).show();
                    MainActivity.mInstance.MuatData(); // memanggil MainActivity untuk memproses method MemuatData()
                    finish(); // keluar
                } else if (status_kode == 2) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 6) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 7) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 8) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 9) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 10) {
                    Toasty.normal(UbahActivity.this, status_pesan, Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.normal(UbahActivity.this, "Status Kesalahan Tidak Diketahui!", Toasty.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                if (error instanceof TimeoutError) {
                    Toasty.error(UbahActivity.this, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NoConnectionError) {
                    Toasty.error(UbahActivity.this, "Nerwork NoConnectionError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(UbahActivity.this, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(UbahActivity.this, "Server Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(UbahActivity.this, "Network Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(UbahActivity.this, "Parse Error", Toasty.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(UbahActivity.this, "Status Error Tidak Diketahui!", Toasty.LENGTH_SHORT, true).show();
                }
            }
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                // set ke params
                HashMap<String, String> hashMap = new HashMap<>();
//                hashMap.put("id", String.valueOf(id));
//                hashMap.put("kode", kode);
//                hashMap.put("nama", nama);
//                hashMap.put("harga", harga);

                hashMap.put("id", String.valueOf(id));
                hashMap.put("nim", nim);
                hashMap.put("nama", nama);
                hashMap.put("alamat", alamat);
                hashMap.put("date", ttl);
                hashMap.put("status", status);

                return hashMap;
            }
        };

        // memanggil AppController dan menambahkan dalam antrin
        // text "edit_data" anda bisa mengganti inisial yang lain
        AppController.getInstance().addToQueue(request, "edit_data");
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

        /*
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /*
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /*
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /*
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /*
                 * Update TextView dengan tanggal yang kita pilih
                 */
                ttl_data.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /*
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    @SuppressLint("ResourceType")
    private void statusDialog() {
        View dialogView = UbahActivity.this.getLayoutInflater().inflate(R.layout.status_dialog, null);
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
                    Toasty.success(UbahActivity.this, "Mahasiswa", Toasty.LENGTH_SHORT, true).show();
                } else if ("Dosen".equals(status[which])) {
                    status_data.setText("Dosen");
                    Toasty.success(UbahActivity.this, "Dosen", Toasty.LENGTH_SHORT, true).show();
                } else if ("Mata Kuliah".equals(status[which])) {
                    status_data.setText("Mata Kuliah");
                    Toasty.success(UbahActivity.this, "Mata Kuliah", Toasty.LENGTH_SHORT, true).show();
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
