package com.example.assigment_walmart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_Country extends RecyclerView.Adapter<Adapter_Country.ViewHolder> {
    private List<Country> countries_list = new ArrayList<>();

    public void setCountries(List<Country> countries) {
        DiffUtil.DiffResult diffResult=DiffUtil.calculateDiff(new Country_diffutil_callback(countries_list,countries));
        countries_list.clear();
        countries_list.addAll(countries);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countries_list.get(position);
        String name = country.getName() != null ? country.getName() : "Unknown Country";
        String region = country.getRegion() != null ? country.getRegion() : "Unknown Region";
        String code = country.getCode() != null ? country.getCode() : "--";
        String capital = country.getCapital() != null ? country.getCapital() : "No Capital";
        holder.nameregioncode.setText(name + ", " + region + "     "+code);
        holder.capital.setText(capital);
    }

    @Override
    public int getItemCount() {
        return countries_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameregioncode, capital;

        public ViewHolder(View itemView) {
            super(itemView);
            nameregioncode = itemView.findViewById(R.id.nameregioncode);
            capital = itemView.findViewById(R.id.capital);
        }
    }
}
