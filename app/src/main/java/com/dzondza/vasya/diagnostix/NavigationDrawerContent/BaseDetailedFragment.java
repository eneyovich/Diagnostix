package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import com.dzondza.vasya.diagnostix.R;
import com.dzondza.vasya.diagnostix.RecyclerItemData;
import com.dzondza.vasya.diagnostix.RecyclerAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * Base fragment, which contains similar information for all fragments
 * in drawerLayout
 */

public abstract class BaseDetailedFragment extends Fragment implements AdapterView.OnItemClickListener {

    protected List<RecyclerItemData> recyclerViewLine = new ArrayList<>();
    protected RecyclerAdapter adapter;


    protected void initializeRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new RecyclerAdapter(recyclerViewLine, this);
        recyclerView.setAdapter(adapter);

        //adds line between items in list
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()));

        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {}

    protected abstract void recyclerListData();
}