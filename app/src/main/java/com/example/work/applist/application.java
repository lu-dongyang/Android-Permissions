package com.example.work.applist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;


import com.example.work.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class application extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
//    Toolbar toolbar;
    boolean mIncludeSystemApps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
       Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        listView = (ListView)findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefresh);
        listView.setTextFilterEnabled(true);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshIt();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadAppInfoTask  loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }

    private void refreshIt() {
        LoadAppInfoTask  loadAppInfoTask = new LoadAppInfoTask();
        loadAppInfoTask.execute(PackageManager.GET_META_DATA);
    }




    class LoadAppInfoTask extends AsyncTask<Integer,Integer, List<AppInfo>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected List<AppInfo> doInBackground(Integer... integers) {
            List<AppInfo> apps = new ArrayList<>();
            PackageManager packageManager = getPackageManager();
            List<ApplicationInfo> infos = packageManager.getInstalledApplications(integers[0]);

            for (ApplicationInfo info:infos){
                if (!mIncludeSystemApps && (info.flags & ApplicationInfo.FLAG_SYSTEM) == 1){
                    continue;
                }

                AppInfo app = new AppInfo();
                app.info = info;
                app.label = (String)info.loadLabel(packageManager);
                apps.add(app);
            }

            return apps;
        }

        @Override
        protected void onPostExecute(List<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            listView.setAdapter(new AppAdapter(application.this,appInfos));
            swipeRefreshLayout.setRefreshing(false);

            Snackbar.make(listView,appInfos.size()+"applications loaded",Snackbar.LENGTH_LONG).show();


        }
    }



}