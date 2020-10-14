package com.example.a1061524_1061525_final.ui.Contribution;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a1061524_1061525_final.R;

public class ContributionFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contribution, container, false);
        Button bt = view.findViewById(R.id.button_open);
        bt.setOnClickListener(open);
        return view;
    }

    private View.OnClickListener open = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.sow.org.tw/donate"));
            startActivity(intent);
        }
    };
}