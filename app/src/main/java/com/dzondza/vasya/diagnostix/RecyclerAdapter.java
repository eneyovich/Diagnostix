package com.dzondza.vasya.diagnostix;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import java.util.List;

/**
 * RecyclerView.Adapter for fragments in DrawerLayout menu
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    protected List<RecyclerItemsData> mRecyclerItemsDataList;
    protected AdapterView.OnItemClickListener onItemClickListener;

    public RecyclerAdapter(List<RecyclerItemsData> recyclerItemsDataList,
                           AdapterView.OnItemClickListener onItemClickListener) {
        this.mRecyclerItemsDataList = recyclerItemsDataList;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items_layout,
                parent, false);

        return new RecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.descriptionTextView.setText(mRecyclerItemsDataList.get(position).description);
        holder.solutionTextView.setText(mRecyclerItemsDataList.get(position).solution);
    }

    @Override
    public int getItemCount() {
        return mRecyclerItemsDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView descriptionTextView;
        TextView solutionTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            descriptionTextView = itemView.findViewById(R.id.text_view_description);
            solutionTextView = itemView.findViewById(R.id.text_view_solution);

            solutionTextView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(null, view, getAdapterPosition(), view.getId());
        }
    }
}