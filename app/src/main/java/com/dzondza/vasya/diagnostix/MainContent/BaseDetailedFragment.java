package com.dzondza.vasya.diagnostix.MainContent;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;
import com.dzondza.vasya.diagnostix.RecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Base fragment, which contains similar information for all fragments
 * in drawerLayout, except InstalledAppsFragment
 */

public abstract class BaseDetailedFragment extends Fragment
        implements AdapterView.OnItemClickListener {

    protected List<RecyclerItemsData> recyclerViewLine;
    protected RecyclerAdapter adapter;


    protected void initializeRecyclerView(View view) {
        recyclerViewLine = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(recyclerViewLine, this);
        recyclerView.setAdapter(adapter);

        //line between items in list
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation()));

        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}
}