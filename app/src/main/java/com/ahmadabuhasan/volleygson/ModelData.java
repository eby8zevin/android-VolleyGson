package com.ahmadabuhasan.volleygson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ModelData {

    @SerializedName("id_data")
    @Expose
    private int id_data;
    @SerializedName("nim_data")
    @Expose
    private int nim_data;
    @SerializedName("nama_data")
    @Expose
    private String name_data;
    @SerializedName("alamat_data")
    @Expose
    private String address_data;
    @SerializedName("ttl_data")
    @Expose
    private Date ttl_data;
    @SerializedName("status_data")
    @Expose
    private String status_data;

    public ModelData(int id_data, int nim_data, String name_data, String address_data, Date ttl_data, String status_data) {
        this.id_data = id_data;
        this.nim_data = nim_data;
        this.name_data = name_data;
        this.address_data = address_data;
        this.ttl_data = ttl_data;
        this.status_data = status_data;
    }

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

    public String getName_data() {
        return name_data;
    }

    public void setName_data(String name_data) {
        this.name_data = name_data;
    }

    public String getAddress_data() {
        return address_data;
    }

    public void setAddress_data(String address_data) {
        this.address_data = address_data;
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