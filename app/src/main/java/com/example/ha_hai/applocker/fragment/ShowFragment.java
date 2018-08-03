package com.example.ha_hai.applocker.fragment;


import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ha_hai.applocker.MainActivity;
import com.example.ha_hai.applocker.R;
import com.example.ha_hai.applocker.adapter.ListAppAdapter;
import com.example.ha_hai.applocker.database.DatabaseManager;
import com.example.ha_hai.applocker.model.App;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {

    ListAppAdapter adapter;
    List<App> mAppsInDevice, mAppsFromDB, mAppsAreLocking, mTempApps;

    RecyclerView recyclerView;
    Spinner spinner;

    ProgressDialog dialog;

    public static DatabaseManager mDBManager;

    public ShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_show, container, false);

        recyclerView = itemView.findViewById(R.id.recyclerView);
        spinner = itemView.findViewById(R.id.spinnner);

        String[] lItems = {"App Installed", "App System"};
        final ArrayAdapter spAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, lItems);
        spAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(spAdapter);

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && adapter != null) {
//                    adapter.setItems(listAppInstall);
                } else if (i == 1 && adapter != null) {
//                    adapter.setItems(listAppSystem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mDBManager = new DatabaseManager(getActivity());

        new MyAsynTask().execute();
        return itemView;
    }

    private void getAllAppFromDevide() {
        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {

            Drawable icon = app.loadIcon(pm);
            String name = app.loadLabel(pm).toString();
            String packageName = app.packageName;

            //type 1: app is installed
            //type 0: it is a system app
            //state 0: lock off
            if ((app.flags & (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP | ApplicationInfo.FLAG_SYSTEM)) > 0) {
                // It is a system app
                mAppsInDevice.add(new App(icon, name, packageName, 0, 0));

            } else {
                // It is installed by the user
                mAppsInDevice.add(new App(icon, name, packageName, 1, 0));
            }
        }

        mTempApps = new ArrayList<>(mAppsInDevice);
    }

    public class MyAsynTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mAppsInDevice = new ArrayList<>();

            //show dialog
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading data...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getAllAppFromDevide();
            mAppsFromDB = mDBManager.getAllApp();
            mAppsAreLocking = mDBManager.getAppsAreLocking();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            syncDataFromDBToUI();

            addNewAppsInstallIntoDb();
            setAdapter();

            //hide dialog
            dialog.dismiss();
        }
    }

    //sync data db with UI
    void syncDataFromDBToUI() {
        int i = 0;
        for (App app : mAppsAreLocking) {
            for (; i < mAppsInDevice.size(); i++) {
                if (!app.getPackageName().equals(mAppsInDevice.get(i).getPackageName())) {
                    continue;
                } else {
                    mAppsInDevice.get(i).setState(1);
                    break;
                }
            }
            if (i == mAppsInDevice.size())
                mDBManager.deleteApp(app);

            i = 0;
        }
    }

    void setAdapter() {
        adapter = new ListAppAdapter(getActivity(), mAppsInDevice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void addNewAppsInstallIntoDb() {
        mTempApps.removeAll(mAppsFromDB);
        for (App app : mTempApps) {
            mDBManager.addApp(app);
            mAppsFromDB.add(app);
        }
    }
}
