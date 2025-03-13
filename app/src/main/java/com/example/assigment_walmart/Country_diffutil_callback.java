package com.example.assigment_walmart;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class Country_diffutil_callback extends DiffUtil.Callback {
    private final List<Country> old_list;
    private final List<Country> new_list;

    public Country_diffutil_callback(List<Country> oldList, List<Country> newList) {
        this.old_list = oldList;
        this.new_list = newList;
    }
    @Override
    public int getOldListSize() {
        return old_list.size();
    }

    @Override
    public int getNewListSize() {
        return new_list.size();
    }

    @Override
    public boolean areItemsTheSame(int old_item_position, int new_item_position) {
        return old_list.get(old_item_position).getCode().equals(new_list.get(new_item_position).getCode());
    }

    @Override
    public boolean areContentsTheSame(int old_item_position, int new_item_position) {
        return old_list.get(old_item_position).equals(new_list.get(new_item_position));
    }
}
