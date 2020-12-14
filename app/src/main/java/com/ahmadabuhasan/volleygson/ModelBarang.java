package com.ahmadabuhasan.volleygson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/*
 * Created by Ahmad Abu Hasan on 14/12/2020
 */

public class ModelBarang {

//    @SerializedName("id") //samakan dengan key/id yang akan kita ambil dari API
//    @Expose
//    private int id;
//    @SerializedName("kode_barang") //samakan dengan key/id yang akan kita ambil dari API
//    @Expose
//    private String kode_barang;
//    @SerializedName("nama_barang") //samakan dengan key/id yang akan kita ambil dari API
//    @Expose
//    private String nama_barang;
//    @SerializedName("harga_barang") //samakan dengan key/id yang akan kita ambil dari API
//    @Expose
//    private int harga_barang;

    @SerializedName("id_data")
    @Expose
    private int id_data;
    @SerializedName("nim_data")
    @Expose
    private int nim_data;
    @SerializedName("nama_data")
    @Expose
    private String nama_data;
    @SerializedName("alamat_data")
    @Expose
    private String alamat_data;
    @SerializedName("ttl_data")
    @Expose
    private Date ttl_data;
    @SerializedName("status_data")
    @Expose
    private String status_data;

//    public ModelBarang(int id, String kode_barang, String nama_barang, int harga_barang) {
//        this.id = id;
//        this.kode_barang = kode_barang;
//        this.nama_barang = nama_barang;
//        this.harga_barang = harga_barang;
//    }

    public ModelBarang(int id_data, int nim_data, String nama_data, String alamat_data, Date ttl_data, String status_data) {
        this.id_data = id_data;
        this.nim_data = nim_data;
        this.nama_data = nama_data;
        this.alamat_data = alamat_data;
        this.ttl_data = ttl_data;
        this.status_data = status_data;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getKode_barang() {
//        return kode_barang;
//    }
//
//    public void setKode_barang(String kode_barang) {
//        this.kode_barang = kode_barang;
//    }
//
//    public String getNama_barang() {
//        return nama_barang;
//    }
//
//    public void setNama_barang(String nama_barang) {
//        this.nama_barang = nama_barang;
//    }
//
//    public int getHarga_barang() {
//        return harga_barang;
//    }
//
//    public void setHarga_barang(int harga_barang) {
//        this.harga_barang = harga_barang;
//    }

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
    }

    public int getNim_data() {
        return nim_data;
    }

    public void setNim_data(int nim_data) {
        this.nim_data = nim_data;
    }

    public String getNama_data() {
        return nama_data;
    }

    public void setNama_data(String nama_data) {
        this.nama_data = nama_data;
    }

    public String getAlamat_data() {
        return alamat_data;
    }

    public void setAlamat_data(String alamat_data) {
        this.alamat_data = alamat_data;
    }

    public Date getTtl_data() {
        return ttl_data;
    }

    public void setTtl_data(Date ttl_data) {
        this.ttl_data = ttl_data;
    }

    public String getStatus_data() {
        return status_data;
    }

    public void setStatus_data(String status_data) {
        this.status_data = status_data;
    }

}
