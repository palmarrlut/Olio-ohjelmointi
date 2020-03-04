package com.example.test.ui.tools;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.test.R;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView textView = root.findViewById(R.id.text_tools);
        final TextView display = root.findViewById(R.id.displayView);
        final EditText fontSize = root.findViewById(R.id.fonttiKoko);
        final EditText heightSize = root.findViewById(R.id.korkeusKoko);
        final EditText widthSize = root.findViewById(R.id.leveysKoko);
        final EditText rowAmount = root.findViewById(R.id.riviKoko);
        final Switch writeEnable = root.findViewById(R.id.switch1);

        //fontSize.setText(String.valueOf(display.getTextSize()));
        //heightSize.setText(String.valueOf(display.getHeight()));
        //widthSize.setText(String.valueOf(display.getWidth()));
        //rowAmount.setText(String.valueOf(display.getLineCount()));
        writeEnable.setChecked(false);

        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return root;
    }

}