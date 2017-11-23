package com.braincorp.orkut2.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.braincorp.orkut2.R;

public class OptionsFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final boolean attachToRoot = false;
        return inflater.inflate(R.layout.fragment_options, container, attachToRoot);
    }

    @Override
    public void onClick(View view) {

    }

}