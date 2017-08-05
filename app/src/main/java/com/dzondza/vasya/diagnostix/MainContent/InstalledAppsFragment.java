package com.dzondza.vasya.diagnostix.MainContent;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.dzondza.vasya.diagnostix.R;
import java.util.List;


/**
 * Installed applications on phone
 */

public class InstalledAppsFragment extends Fragment {

    private PackageManager mPackageManager;
    private List<ApplicationInfo> appsInfoList;

    private LayoutInflater inflater;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_installed_apps, container, false);

        //getting installed apps' list
        mPackageManager = getActivity().getPackageManager();
        appsInfoList = mPackageManager
                .getInstalledApplications(PackageManager.GET_META_DATA);


        ListView listView = view.findViewById(R.id.listview_instaled_apps);
        listView.setAdapter(new AppsListAdapter());


        //apps' system info on listView item touch
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ApplicationInfo item = (ApplicationInfo) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + item.packageName));
                startActivity(intent);
            }
        });


        //toolbar title
        getActivity().setTitle(R.string.drawer_applications);

        return view;
    }


    //adapter for listView to represents list of apps
    private class AppsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return appsInfoList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return appsInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View v, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (v == null) {
                viewHolder = new ViewHolder();
                view = inflater.inflate(R.layout.fragment_installed_apps, viewGroup, false);


                viewHolder.appIconImageView = (ImageView) view
                        .findViewById(R.id.installed_apps_image_view);
                viewHolder.appNameTextView = (TextView) view
                        .findViewById(R.id.installed_apps_text_view);
                viewHolder.appSourceDirTextView = (TextView) view
                        .findViewById(R.id.installed_apps_text_view2);

                view.setTag(viewHolder);

            } else {
                view = v;
                viewHolder = (ViewHolder) view.getTag();
            }

            final ApplicationInfo appInfo = appsInfoList.get(i);

            viewHolder.appIconImageView.setImageDrawable(appInfo.loadIcon(mPackageManager));
            viewHolder.appNameTextView.setText(appInfo.loadLabel(mPackageManager).toString());
            viewHolder.appSourceDirTextView.setText(appInfo.sourceDir);


            return view;
        }
    }


    private static class ViewHolder {
        private ImageView appIconImageView;
        private TextView appNameTextView;
        private TextView appSourceDirTextView;
    }
}