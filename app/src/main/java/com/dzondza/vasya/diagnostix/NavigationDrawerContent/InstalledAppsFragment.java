package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.dzondza.vasya.diagnostix.R;
import com.dzondza.vasya.diagnostix.RecyclerAdapterInstalledApps;
import com.dzondza.vasya.diagnostix.RecyclerItemData;

import java.util.List;

/**
 * Installed applications on phone
 */

public class InstalledAppsFragment extends BaseDetailedFragment {
    private List<ApplicationInfo> mAppsInfoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        activateRecycler(view);

        recyclerListData();

        //toolbar title
        getActivity().setTitle(R.string.drawer_applications);

        return view;
    }


    private void activateRecycler(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setAdapter(new RecyclerAdapterInstalledApps(recyclerViewLine, this));

        //adds line between items in list
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()));

        recyclerView.setHasFixedSize(true);
    }


    protected void recyclerListData() {
        PackageManager mPackageManager = getActivity().getPackageManager();
        mAppsInfoList = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo appInfo: mAppsInfoList) {
            recyclerViewLine.add(new RecyclerItemData(appInfo.loadIcon(mPackageManager),
                    appInfo.loadLabel(mPackageManager).toString(), appInfo.sourceDir));
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i >= 0 && i < mAppsInfoList.size()) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + mAppsInfoList.get(i).packageName));
            startActivity(intent);
        }
        super.onItemClick(adapterView, view, i, l);
    }
}