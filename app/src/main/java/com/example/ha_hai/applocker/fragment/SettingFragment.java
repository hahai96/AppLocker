package com.example.ha_hai.applocker.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ha_hai.applocker.R;
import com.example.ha_hai.applocker.RequestPasswordActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    Button btnBat, btnTat;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_setting, container, false);

        btnBat = itemView.findViewById(R.id.btnBat);
        btnTat = itemView.findViewById(R.id.btnTat);

        btnBat.setOnClickListener(this);
        btnTat.setOnClickListener(this);
        return itemView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBat:
                Intent intent = new Intent(getActivity(), RequestPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btnTat:
                Intent i = new Intent(getActivity(), RequestPasswordActivity.class);
                startActivity(i);
                break;
        }
    }
}
