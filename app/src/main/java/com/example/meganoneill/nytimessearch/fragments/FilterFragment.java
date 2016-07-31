package com.example.meganoneill.nytimessearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.meganoneill.nytimessearch.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by meganoneill on 7/27/16.
 */
public class FilterFragment extends DialogFragment {
    EditText date;

    public FilterFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterFragment newInstance(String title) {
        FilterFragment frag = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        date = (EditText) view.findViewById(R.id.etStartDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(FilterFragment.this, 300);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

    }

    public void setDate(Calendar c) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        date.setText(format.format(c.getTime()));
    }




}
