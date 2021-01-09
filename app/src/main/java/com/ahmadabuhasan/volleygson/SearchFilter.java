package com.ahmadabuhasan.volleygson;

import android.widget.Filter;

import java.util.ArrayList;

/*
 * Created by Ahmad Abu Hasan on 08/01/2021
 */

public class SearchFilter extends Filter {

    ArrayList<ModelBarang> filterList;
    RecyclerViewAdapter recyclerViewAdapter;

    public SearchFilter(ArrayList<ModelBarang> filterList, RecyclerViewAdapter recyclerViewAdapter) {
        this.filterList = filterList;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<ModelBarang> filteredData = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getNama_data().toUpperCase().contains(constraint)) {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredData.add(filterList.get(i));
                }
            }
            results.count = filteredData.size();
            results.values = filteredData;
        } else {
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        recyclerViewAdapter.arrayModelBarangs = (ArrayList<ModelBarang>) results.values;
        //REFRESH
        recyclerViewAdapter.notifyDataSetChanged();
    }
}