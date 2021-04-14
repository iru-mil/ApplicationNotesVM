package ru.geekbrains.applicationnotesvm.ui.notes;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class DiffUtilCallBack extends DiffUtil.Callback {
    private List<AdapterItem> items;
    private List<AdapterItem> toSet;

    public DiffUtilCallBack(List<AdapterItem> items, List<AdapterItem> toSet) {
        this.items = items;
        this.toSet = toSet;
    }

    @Override
    public int getOldListSize() {
        return items.size();
    }

    @Override
    public int getNewListSize() {
        return toSet.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return items.get(oldItemPosition).uniqueTag().equals(toSet.get(newItemPosition).uniqueTag());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return items.get(oldItemPosition).equals(toSet.get(newItemPosition));
    }
}
