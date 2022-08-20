package com.ahmadabuhasan.volleygson;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {

    private final Context context;
    ArrayList<ModelData> arrayModelData, dataFilter;
    SearchFilter searchFilter;

    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    public RecyclerViewAdapter(Context context, ArrayList<ModelData> arrayModelData) {
        this.context = context;
        this.arrayModelData = arrayModelData;
        this.dataFilter = arrayModelData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelData modelData = arrayModelData.get(position);
        String formatDate = dateFormatter.format(modelData.getTtl_data());

        holder.nim.setText(this.context.getString(R.string.nim) + " : " + modelData.getNim_data());
        holder.name.setText(this.context.getString(R.string.name) + " : " + modelData.getName_data());
        holder.address.setText(this.context.getString(R.string.address) + " : " + modelData.getAddress_data());
        holder.ttl.setText(this.context.getString(R.string.ttl) + " : " + formatDate);
        holder.status.setText(this.context.getString(R.string.status) + " : " + modelData.getStatus_data());
    }

    @Override
    public int getItemCount() {
        return arrayModelData.size();
    }

    @Override
    public Filter getFilter() {
        if (searchFilter == null) {
            searchFilter = new SearchFilter(dataFilter, this);
        }
        return searchFilter;
    }

    public void clear() {
        arrayModelData.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nim;
        private final TextView name;
        private final TextView address;
        private final TextView ttl;
        private final TextView status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nim = itemView.findViewById(R.id.nim);
            name = itemView.findViewById(R.id.nama);
            address = itemView.findViewById(R.id.alamat);
            ttl = itemView.findViewById(R.id.ttl);
            status = itemView.findViewById(R.id.status);

            itemView.setOnClickListener(v -> {
                final int position = getAbsoluteAdapterPosition();
                final ModelData modelData = arrayModelData.get(position);
                String[] choose = {"Detail", "Edit", "Delete"};

                new AlertDialog.Builder(context)
                        .setTitle("---Choose---")
                        .setItems(choose, (dialog, which) -> {
                            if (which == 0) {
                                viewData(modelData);
                                Toasty.info(context, "Detail", Toasty.LENGTH_SHORT, true).show();
                            } else if (which == 1) {
                                updateData(modelData);
                                Toasty.info(context, "Edit", Toasty.LENGTH_SHORT, true).show();
                            } else if (which == 2) {
                                Toasty.info(context, "Delete", Toasty.LENGTH_SHORT, true).show();

                                AlertDialog.Builder dialog_delete = new AlertDialog.Builder(context);
                                dialog_delete.setTitle("Delete Data");
                                dialog_delete.setCancelable(false);
                                dialog_delete.setMessage("Are you sure you want to delete?");
                                dialog_delete.setNegativeButton("Cancel", (dialog1, which1) -> dialog1.cancel());
                                dialog_delete.setPositiveButton("Yes", (dialog0, which0) -> deleteData(position, modelData));
                                dialog_delete.show();
                            }
                        })
                        .create()
                        .show();
            });
        }

        private void viewData(@NonNull ModelData modelData) {
            String formatDate = dateFormatter.format(modelData.getTtl_data());
            String details = "NIM:" + modelData.getNim_data() +
                    "\nName: " + modelData.getName_data() +
                    "\nAddress: " + modelData.getAddress_data() +
                    "\nTTL: " + formatDate +
                    "\nStatus: " + modelData.getStatus_data();

            new AlertDialog.Builder(context)
                    .setTitle("Details")
                    .setMessage(details)
                    .setPositiveButton("Ok!", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }

        private void updateData(@NonNull ModelData modelData) {
            Intent intent = new Intent(context, UpdateActivity.class);
            String formatDate = dateFormatter.format(modelData.getTtl_data());

            intent.putExtra("et_id", modelData.getId_data());
            intent.putExtra("et_nim", modelData.getNim_data());
            intent.putExtra("et_name", modelData.getName_data());
            intent.putExtra("et_address", modelData.getAddress_data());
            intent.putExtra("et_ttl", formatDate);
            intent.putExtra("et_status", modelData.getStatus_data());
            context.startActivity(intent);
        }

        private void deleteData(final int position, @NonNull ModelData modelData) {
            String url = "https://blackpink-marketplace.000webhostapp.com/tugas/api/delete?id=" + modelData.getId_data();
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                ResponseStatus responseStatus = new Gson().fromJson(response, ResponseStatus.class);
                int status_kode = responseStatus.getStatus_code();
                String status_message = responseStatus.getStatus_message();

                if (status_kode == 1) {
                    Toasty.success(context, status_message, Toasty.LENGTH_SHORT, true).show();
                    notifyItemRemoved(position);
                    arrayModelData.remove(position);
                } else if (status_kode == 2) {
                    Toasty.normal(context, status_message, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 3) {
                    Toasty.normal(context, status_message, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 4) {
                    Toasty.normal(context, status_message, Toasty.LENGTH_SHORT).show();
                } else if (status_kode == 5) {
                    Toasty.normal(context, status_message, Toasty.LENGTH_SHORT).show();
                } else {
                    Toasty.normal(context, "Status Unknown error!", Toasty.LENGTH_SHORT).show();
                }
            }, error -> {
                if (error instanceof TimeoutError) {
                    Toasty.error(context, "Network TimeoutError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NoConnectionError) {
                    Toasty.error(context, "Network NoConnectionError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(context, "Network AuthFailureError", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(context, "Server Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(context, "Network Error", Toasty.LENGTH_SHORT, true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(context, "Parse Error", Toasty.LENGTH_SHORT, true).show();
                } else {
                    Toasty.error(context, "Unknown Error Status!", Toasty.LENGTH_SHORT, true).show();
                }
            });

            AppController.getInstance().addToQueue(request, "delete_data");
        }
    }
}