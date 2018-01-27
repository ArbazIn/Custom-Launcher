package com.techno.minilauncher.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.techno.minilauncher.R;
import com.techno.minilauncher.model.AppListMain;
import com.techno.minilauncher.model.WallpaperMain;

import java.util.ArrayList;

/**
 * Created by thetaubuntu5 on 14/12/17.
 */

public class WallpaperListAdapter extends RecyclerView.Adapter<WallpaperListAdapter.ViewHolder> {

    ArrayList<WallpaperMain> wallpaperMainArrayList;
    WallpaperMain wallpaperMain;
    Context context;
    public SimpleDraweeView sdvWallpaperList;

    public WallpaperListAdapter(Context context, ArrayList<WallpaperMain> wallpaperMainArrayList) {
        this.context = context;
        this.wallpaperMainArrayList = wallpaperMainArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wallpaper_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            wallpaperMain = wallpaperMainArrayList.get(position);
            Uri uri= Uri.parse(wallpaperMain.getImgUrl());
            sdvWallpaperList.setImageURI(uri);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return wallpaperMainArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;



        public ViewHolder(View view) {
            super(view);
            mView = view;
            sdvWallpaperList = view.findViewById(R.id.sdvWallpaperList);
        }
    }
}
