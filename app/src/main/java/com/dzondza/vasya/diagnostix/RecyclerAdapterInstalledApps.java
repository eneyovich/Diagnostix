package com.dzondza.vasya.diagnostix;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * RecyclerView.Adapter for InstalledAppsFragment in drawerLayout menu
 */

public class RecyclerAdapterInstalledApps extends RecyclerView.Adapter<RecyclerAdapterInstalledApps.ViewHolder> {
    private List<RecyclerItemData> mRecyclerItemDataList;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public RecyclerAdapterInstalledApps(List<RecyclerItemData> recyclerItemDataList,
                                        AdapterView.OnItemClickListener onItemClickListener) {
        mRecyclerItemDataList = recyclerItemDataList;
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_installed_apps_item_layout, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.appImage.setImageDrawable(mRecyclerItemDataList.get(position).mAppImage);
        holder.appTitleText.setText(mRecyclerItemDataList.get(position).mDescription);
        holder.appDetailsText.setText(mRecyclerItemDataList.get(position).mSolution);
    }

    @Override
    public int getItemCount() {
        return mRecyclerItemDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView appImage;
        TextView appTitleText;
        TextView appDetailsText;

        public ViewHolder(View itemView) {
            super(itemView);
            appImage = itemView.findViewById(R.id.image_installed_apps);
            appTitleText = itemView.findViewById(R.id.text_installed_apps_title);
            appDetailsText = itemView.findViewById(R.id.text_installed_apps_detailed);

            appTitleText.setOnClickListener(this);
            appDetailsText.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}