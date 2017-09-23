package com.dzondza.vasya.diagnostix;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import java.util.List;

/**
 * RecyclerView.Adapter for fragments in drawerLayout menu, except InstalledAppsFragment
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<RecyclerItemData> mRecyclerItemDataList;
    private AdapterView.OnItemClickListener mOnItemClickListener;


    public RecyclerAdapter(List<RecyclerItemData> recyclerItemDataList,
                           AdapterView.OnItemClickListener onItemClickListener) {
        this.mRecyclerItemDataList = recyclerItemDataList;
        this.mOnItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recycler_item_layout,
                parent, false);

        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.mDescriptionTextView.setText(mRecyclerItemDataList.get(position).mDescription);
        holder.mSolutionTextView.setText(mRecyclerItemDataList.get(position).mSolution);
    }

    @Override
    public int getItemCount() {
        return mRecyclerItemDataList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mDescriptionTextView;
        TextView mSolutionTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            mDescriptionTextView = itemView.findViewById(R.id.text_recycler_item_description);
            mSolutionTextView = itemView.findViewById(R.id.text_recycler_item_solution);

            mSolutionTextView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}