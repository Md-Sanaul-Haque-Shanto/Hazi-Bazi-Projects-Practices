package com.example.shanto.lab18.provatsoft.apps.flag71;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.provatsoft.apps.flag71.models.FlagItem;
import com.provatsoft.apps.picatorlib.ImageManager;
import java.util.ArrayList;

public class FlagGridAdapter extends BaseAdapter {
    private Context context;
    private final ImageManager imageManager;
    private final ArrayList<FlagItem> mobileValues;

    public FlagGridAdapter(Context context, ArrayList<FlagItem> flagItems) {
        this.context = context;
        this.mobileValues = flagItems;
        this.imageManager = new ImageManager();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        if (convertView != null) {
            return convertView;
        }
        View gridView = inflater.inflate(C0467R.layout.flag_item, null);
        FlagItem flagItem = (FlagItem) this.mobileValues.get(position);
        ((TextView) gridView.findViewById(C0467R.id.grid_item_label)).setText(flagItem.getFlagName());
        ((ImageView) gridView.findViewById(C0467R.id.grid_item_image)).setImageBitmap(ImageManager.overlay(this.imageManager.resize(((BitmapDrawable) ContextCompat.getDrawable(this.context, C0467R.drawable.icon_user_default)).getBitmap(), 150, 150), this.imageManager.resize(((BitmapDrawable) ContextCompat.getDrawable(this.context, flagItem.getDisplayableFlagId())).getBitmap(), 150, 150)));
        return gridView;
    }

    public int getCount() {
        return this.mobileValues.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
