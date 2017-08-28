package com.dzondza.vasya.diagnostix.NavigationDrawerContent;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
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
    private List<ApplicationInfo> mAppsInfoList;

    private LayoutInflater mInflater;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mInflater = inflater;
        mView = inflater.inflate(R.layout.fragment_installed_apps, container, false);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                //getting installed apps' list
                mPackageManager = getActivity().getPackageManager();
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                mAppsInfoList = mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ListView listView = mView.findViewById(R.id.listview_instaled_apps);
                listView.setAdapter(new AppsListAdapter());

                //shows applications' system information after touch on item
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        ApplicationInfo item = (ApplicationInfo) adapterView.getItemAtPosition(i);
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + item.packageName));
                        startActivity(intent);
                    }
                });
                super.onPostExecute(aVoid);
            }
        }.execute();


        //toolbar title
        getActivity().setTitle(R.string.drawer_applications);

        return mView;
    }


    //adapter for listView to represent list of apps
    private class AppsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mAppsInfoList.size();
        }

        @Override
        public ApplicationInfo getItem(int position) {
            return mAppsInfoList.get(position);
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
                mView = mInflater.inflate(R.layout.fragment_installed_apps, viewGroup, false);


                viewHolder.appIconImageView = (ImageView) mView
                        .findViewById(R.id.image_installed_apps);
                viewHolder.appNameTextView = (TextView) mView
                        .findViewById(R.id.text_installed_apps_descript);
                viewHolder.appSourceDirTextView = (TextView) mView
                        .findViewById(R.id.text_installed_apps_detailed);

                mView.setTag(viewHolder);

            } else {
                mView = v;
                viewHolder = (ViewHolder) mView.getTag();
            }

            final ApplicationInfo appInfo = mAppsInfoList.get(i);

            viewHolder.appIconImageView.setImageDrawable(appInfo.loadIcon(mPackageManager));
            viewHolder.appNameTextView.setText(appInfo.loadLabel(mPackageManager).toString());
            viewHolder.appSourceDirTextView.setText(appInfo.sourceDir);


            return mView;
        }
    }


    private static class ViewHolder {
        private ImageView appIconImageView;
        private TextView appNameTextView;
        private TextView appSourceDirTextView;
    }
}