package com.ahmadabuhasan.volleygson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

/*
 * Created by Ahmad Abu Hasan on 14/12/2020
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ModelBarang> arrayModelBarangs;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    // membuat kontruksi recyclerviewadapter
    public RecyclerViewAdapter(Context context, ArrayList<ModelBarang> arrayModelBarangs) {
        this.context = context;
        this.arrayModelBarangs = arrayModelBarangs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // mendapatkan posisi item
        ModelBarang modelBarang = arrayModelBarangs.get(position);

        // menset data
//        holder.kode_barang.setText(modelBarang.getKode_barang());
//        holder.nama_barang.setText(modelBarang.getNama_barang());
//        holder.harga_barang.setText("Rp. " + modelBarang.getHarga_barang());

        String formatDate = dateFormatter.format(modelBarang.getTtl_data());

        //holder.nim.setText(String.valueOf(modelBarang.getNim_data()));
        TextView textView = holder.nim;
        textView.setText(this.context.getString(R.string.nim) + " : " + modelBarang.getNim_data());
        //holder.nama.setText(modelBarang.getNama_data());
        TextView textView1 = holder.nama;
        textView1.setText(this.context.getString(R.string.nama) + " : " + modelBarang.getNama_data());
        //holder.alamat.setText(modelBarang.getAlamat_data());
        TextView textView2 = holder.alamat;
        textView2.setText(this.context.getString(R.string.alamat) + " : " + modelBarang.getAlamat_data());
        //holder.ttl.setText(formatDate);
        TextView textView3 = holder.ttl;
        textView3.setText(this.context.getString(R.string.ttl) + " : " + formatDate);
        //holder.status.setText(modelBarang.getStatus_data());
        TextView textView4 = holder.status;
        textView4.setText(this.context.getString(R.string.status) + " : " + modelBarang.getStatus_data());
    }

    @Override
    public int getItemCount() {
        // mengembalikan data set
        return arrayModelBarangs.size();
    }

    // membuat class viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {

        // implementasi textview
//        private TextView kode_barang, nama_barang, harga_barang;

        private TextView nim, nama, alamat, ttl, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // mendeklarasi textview
//            kode_barang = itemView.findViewById(R.id.kode_barang);
//            nama_barang = itemView.findViewById(R.id.nama_barang);
//            harga_barang = itemView.findViewById(R.id.harga_barang);

            nim = itemView.findViewById(R.id.nim);
            nama = itemView.findViewById(R.id.nama);
            alamat = itemView.findViewById(R.id.alamat);
            ttl = itemView.findViewById(R.id.ttl);
            status = itemView.findViewById(R.id.status);

            // mendeklarasi item ketika diklik
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mendapatkan posisi pada adapter
                    final int position = getAdapterPosition();
                    // mengambil posisi pada arraymodelbarang
                    final ModelBarang modelBarang = arrayModelBarangs.get(position);

//                    String[] pilihan = {"Lihat", "Ubah", "Hapus"};
                    String[] pilihan = {"Detail", "Edit", "Delete"};
                    // menamplkan pilihan alertdialog
                    new AlertDialog.Builder(context)
//                            .setTitle("Pilihan")
                            .setTitle("---Choose---")
                            .setItems(pilihan, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) { // 0 sama dengan Lihat
                                        lihatDataBarang(modelBarang);
                                        Toasty.info(context, "Detail", Toasty.LENGTH_SHORT, true).show();
                                    } else if (which == 1) { // 1 sama dengan Ubah
                                        ubahDataBarang(modelBarang);
                                        Toasty.info(context, "Edit", Toasty.LENGTH_SHORT, true).show();
                                    } else if (which == 2) { // 2 sama dengan Hapus
                                        Toasty.info(context, "Delete", Toasty.LENGTH_SHORT, true).show();
                                        AlertDialog.Builder dialog_delete = new AlertDialog.Builder(context);
                                        dialog_delete.setTitle("Delete Data");
                                        dialog_delete.setCancelable(false);
                                        dialog_delete.setMessage("Are you sure you want to delete?");
                                        dialog_delete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                        dialog_delete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                hapusDataBarang(position, modelBarang);
                                            }
                                        });
                                        dialog_delete.show();
                                    }
                                }
                            })
                            .create()
                            .show();
                }
            });
        }

        // method untuk melihat data barang
        private void lihatDataBarang(@NonNull ModelBarang modelBarang) {
            // membuat rangkaiyan text deskripsi
            // fungsi tanda "\n" sama dengan ENTER.
//            String deskripsi = "Kode: " + modelBarang.getKode_barang() +
//                    "\nNama: " + modelBarang.getNama_barang() +
//                    "\nHarga: Rp. " + modelBarang.getHarga_barang();

            String formatDate = dateFormatter.format(modelBarang.getTtl_data());

            String details = "NIM:" + modelBarang.getNim_data() +
                    "\nNama: " + modelBarang.getNama_data() +
                    "\nAlamat: " + modelBarang.getAlamat_data() +
                    "\nTTL: " + formatDate +
                    "\nStatus: " + modelBarang.getStatus_data();

            // menampilkan deskripsi item ketika dipilih
            new AlertDialog.Builder(context)
//                    .setTitle("Deskripsi Barang")
//                    .setMessage(deskripsi)
                    .setTitle("Details")
                    .setMessage(details)
                    .setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
        }

        // method untuk mengubah data barang
        private void ubahDataBarang(@NonNull ModelBarang modelBarang) {
            // pindah ke UpdateActivity dan membawa data
            Intent intent = new Intent(context, UbahActivity.class);
//            intent.putExtra("ed_id", modelBarang.getId());
//            intent.putExtra("ed_kode", modelBarang.getKode_barang());
//            intent.putExtra("ed_nama", modelBarang.getNama_barang());
//            intent.putExtra("ed_harga", modelBarang.getHarga_barang());

            String formatDate = dateFormatter.format(modelBarang.getTtl_data());

            intent.putExtra("et_id", modelBarang.getId_data());
            intent.putExtra("et_nim", modelBarang.getNim_data());
            intent.putExtra("et_nama", modelBarang.getNama_data());
            intent.putExtra("et_alamat", modelBarang.getAlamat_data());
            intent.putExtra("et_ttl", formatDate);
            intent.putExtra("et_status", modelBarang.getStatus_data());
            context.startActivity(intent);
        }

        // method untuk menghapus data barang
        private void hapusDataBarang(final int position, @NonNull ModelBarang modelBarang) {
//            String url = "https://subkode.000webhostapp.com/volley_db/hapus_barang?id=" + modelBarang.getId();
            String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/delete?id=" + modelBarang.getId_data();
            // buat StringRequest volley dan jangan lupa requestnya GET "Request.Method.GET"
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // menerapkan ke model class menggunakan GSON
                    // mengkonversi JSON ke java object
                    ResponStatus responStatus = new Gson().fromJson(response, ResponStatus.class);
                    int status_kode = responStatus.getStatus_kode();
                    String status_pesan = responStatus.getStatus_pesan();

                    // validasi status kode
                    if (status_kode == 1) {
                        // menampilkan toast berhasil
                        Toasty.success(context, status_pesan, Toasty.LENGTH_SHORT, true).show();

                        // menghapus pada tampilan arraylist!
                        notifyItemRemoved(position);
                        arrayModelBarangs.remove(position);
                    } else if (status_kode == 2) {
                        Toasty.normal(context, status_pesan, Toasty.LENGTH_SHORT).show();
                    } else if (status_kode == 3) {
                        Toasty.normal(context, status_pesan, Toasty.LENGTH_SHORT).show();
                    } else if (status_kode == 4) {
                        Toasty.normal(context, status_pesan, Toasty.LENGTH_SHORT).show();
                    } else if (status_kode == 5) {
                        Toasty.normal(context, status_pesan, Toasty.LENGTH_SHORT).show();
                    } else {
                        Toasty.normal(context, "Status Kesalahan Tidak Diketahui!", Toasty.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // jika respon tidak ditemukan maka akan menampilkan berbagai error berikut ini
                    if (error instanceof TimeoutError) {
                        Toasty.error(context, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
                    } else if (error instanceof NoConnectionError) {
                        Toasty.error(context, "Nerwork NoConnectionError", Toasty.LENGTH_SHORT, true).show();
                    } else if (error instanceof AuthFailureError) {
                        Toasty.error(context, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
                    } else if (error instanceof ServerError) {
                        Toasty.error(context, "Server Error", Toasty.LENGTH_SHORT, true).show();
                    } else if (error instanceof NetworkError) {
                        Toasty.error(context, "Network Error", Toasty.LENGTH_SHORT, true).show();
                    } else if (error instanceof ParseError) {
                        Toasty.error(context, "Parse Error", Toasty.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(context, "Status Error Tidak Diketahui!", Toasty.LENGTH_SHORT, true).show();
                    }
                }
            });

            // memanggil AppController dan menambahkan dalam antrin
            // text "hapus_barang" anda bisa mengganti inisial yang lain
            AppController.getInstance().addToQueue(request, "hapus_barang");
        }
    }

}
