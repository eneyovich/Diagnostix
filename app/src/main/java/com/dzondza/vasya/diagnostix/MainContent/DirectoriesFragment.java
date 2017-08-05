package com.dzondza.vasya.diagnostix.MainContent;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dzondza.vasya.diagnostix.RecyclerItemsData;
import com.dzondza.vasya.diagnostix.R;


/**
 * contains directories' information for each device
 */

public class DirectoriesFragment extends BaseDetailedFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragments_recyclerview, container, false);

        // init recyclerView List
        initializeRecyclerView(view);


        String data = getString(R.string.directory_data);
        recyclerViewLine.add(new RecyclerItemsData(data,  Environment.getDataDirectory().toString()));


        String cache = getString(R.string.directory_cache);
        recyclerViewLine.add(new RecyclerItemsData(cache, Environment.getDownloadCacheDirectory().toString()));


        String root = getString(R.string.directory_root);
        recyclerViewLine.add(new RecyclerItemsData(root, Environment.getRootDirectory().toString()));


        String primaryStorageDescript = getString(R.string.directory_primary_storage);
        String primaryStorage = Environment.getExternalStorageDirectory().toString();
        recyclerViewLine.add(new RecyclerItemsData(primaryStorageDescript, primaryStorage));


        String DCIMDescript = getString(R.string.directory_dcim);
        String DCIM = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DCIM).toString();
        recyclerViewLine.add(new RecyclerItemsData(DCIMDescript, DCIM));


        String alarmsDescript = getString(R.string.directory_alarms);
        String alarms = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_ALARMS).toString();
        recyclerViewLine.add(new RecyclerItemsData(alarmsDescript, alarms));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String documentsDescript = getString(R.string.directory_documents);
            String documents = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOCUMENTS).toString();
            recyclerViewLine.add(new RecyclerItemsData(documentsDescript, documents));
        }


        String downloadsDescript = getString(R.string.directory_downloads);
        String downloads = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS).toString();
        recyclerViewLine.add(new RecyclerItemsData(downloadsDescript, downloads));


        String moviesDescript = getString(R.string.directory_movies);
        String movies = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_MOVIES).toString();
        recyclerViewLine.add(new RecyclerItemsData(moviesDescript, movies));


        String musicDescript = getString(R.string.directory_music);
        String music = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_MUSIC).toString();
        recyclerViewLine.add(new RecyclerItemsData(musicDescript, music));


        String notificationsDescript = getString(R.string.directory_notifications);
        String notifications = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_NOTIFICATIONS).toString();
        recyclerViewLine.add(new RecyclerItemsData(notificationsDescript, notifications));


        String picturesDescript = getString(R.string.directory_pictures);
        String pictures = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PICTURES).toString();
        recyclerViewLine.add(new RecyclerItemsData(picturesDescript, pictures));


        String podcastsDescript = getString(R.string.directory_podcasts);
        String podcasts = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_PODCASTS).toString();
        recyclerViewLine.add(new RecyclerItemsData(podcastsDescript, podcasts));


        String ringtonesDescript = getString(R.string.directory_ringtones);
        String ringtones = Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_RINGTONES).toString();
        recyclerViewLine.add(new RecyclerItemsData(ringtonesDescript, ringtones));


        String storageState = getString(R.string.directory_storage_state);
        recyclerViewLine.add(new RecyclerItemsData(storageState, Environment.getExternalStorageState()));


        //toolbar title
        getActivity().setTitle(R.string.drawer_directories);

        return view;
    }
}
