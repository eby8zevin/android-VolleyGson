package com.ahmadabuhasan.volleygson;

import android.widget.Filter;

import java.util.ArrayList;

public class SearchFilter extends Filter {

    ArrayList<ModelData> filterList;
    RecyclerViewAdapter recyclerViewAdapter;

    public SearchFilter(ArrayList<ModelData> filterList, RecyclerViewAdapter recyclerViewAdapter) {
        this.filterList = filterList;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelData> filteredData = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                if (filterList.get(i).getName_data().toUpperCase().contains(constraint)) {
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

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        recyclerViewAdapter.arrayModelData = (ArrayList<ModelData>) results.values;
        recyclerViewAdapter.notifyDataSetChanged();
    }
}