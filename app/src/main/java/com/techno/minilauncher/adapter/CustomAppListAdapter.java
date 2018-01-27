package com.techno.minilauncher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techno.minilauncher.model.AppListMain;
import com.techno.minilauncher.R;

import java.util.ArrayList;

/**
 * Created by thetaubuntu5 on 14/12/17.
 */

public class CustomAppListAdapter extends RecyclerView.Adapter<CustomAppListAdapter.ViewHolder> {

    ArrayList<AppListMain> appListMainArrayList;
    AppListMain appListMain;
    Context context;

    public CustomAppListAdapter(Context context, ArrayList<AppListMain> appListMainArrayList) {
        this.context = context;
        this.appListMainArrayList = appListMainArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            appListMain = appListMainArrayList.get(position);
            holder.ivAppIcon.setImageDrawable(appListMainArrayList.get(position).getAppIcon());
            holder.tvAppLabel.setText(appListMainArrayList.get(position).getAppName());
            holder.tvAppPackage.setText(appListMainArrayList.get(position).getAppPackage());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return appListMainArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ImageView ivAppIcon;
        public TextView tvAppLabel, tvAppPackage;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivAppIcon = view.findViewById(R.id.ivAppIcon);
            tvAppLabel = view.findViewById(R.id.tvAppLabel);
            tvAppPackage = view.findViewById(R.id.tvAppPackage);
        }
    }

    public void updateList(ArrayList<AppListMain> appListMainArrayList,int position) {
        appListMainArrayList.remove(position);
        this.appListMainArrayList = appListMainArrayList;
        notifyDataSetChanged();
    }
}
