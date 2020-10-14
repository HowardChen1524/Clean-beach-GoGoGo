package com.example.a1061524_1061525_final.ui.buildAndJoin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.a1061524_1061525_final.R;

public class buildAndJoinFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buildandjoin, container, false);
        Button build = view.findViewById(R.id.button_build);
        build.setOnClickListener(buildClick);
        Button join = view.findViewById(R.id.button_join);
        join.setOnClickListener(joinClick);
        Button myEvent = view.findViewById(R.id.button_myEvent);
        myEvent.setOnClickListener(eventClick);
        return view;
    }

    private View.OnClickListener buildClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),buildAndJoin_build.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener joinClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),buildAndJoin_join.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener eventClick = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),buildAndJoin_myEvent.class);
            startActivity(intent);
        }
    };
}

