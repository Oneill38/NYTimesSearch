package com.example.meganoneill.nytimessearch.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meganoneill.nytimessearch.R;
import com.example.meganoneill.nytimessearch.models.SearchFilter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by meganoneill on 7/27/16.
 */
public class FilterFragment extends DialogFragment{
    Spinner spinner;
    OnFiltersAppliedListener theCallback;
    TextView theDate;
    String the_date;

    public interface OnFiltersAppliedListener {
        public void onFiltersGiven(String date, String sort, Boolean arts, Boolean fashion, Boolean sports);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            theCallback = (OnFiltersAppliedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public FilterFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static FilterFragment newInstance(SearchFilter searchFilter) {
        FilterFragment frag = new FilterFragment();
        frag.setArguments(searchFilter.toBundle());
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

        Button save = (Button) view.findViewById(R.id.btnFilter);
        theDate = (TextView) view.findViewById(R.id.etStartDate);
        theDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(FilterFragment.this, 300);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(v);
            }
        });
    }

    public void sendData(View v){
        TextView startDate = (TextView) getDialog().findViewById(R.id.etStartDate);
        the_date = startDate.getText().toString();

        spinner = (Spinner) getDialog().findViewById(R.id.spSort);
        String sort = spinner.getSelectedItem().toString();

        CheckBox checkFashion = (CheckBox) getDialog().findViewById(R.id.cbFashion);
        Boolean fashion = checkFashion.isChecked();

        CheckBox checkArts = (CheckBox) getDialog().findViewById(R.id.cbArts);
        Boolean arts = checkArts.isChecked();

        CheckBox checkSports = (CheckBox) getDialog().findViewById(R.id.cbSports);
        Boolean sports = checkSports.isChecked();

        theCallback.onFiltersGiven(the_date, sort, arts, fashion, sports);
        getDialog().dismiss();
    }

    public void setDate(Calendar c) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        theDate.setText(format.format(c.getTime()));
    }




}
