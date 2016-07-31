package com.example.meganoneill.nytimessearch.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meganoneill.nytimessearch.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by meganoneill on 7/27/16.
 */
public class FilterFragment extends DialogFragment implements TextView.OnEditorActionListener {
    EditText date;
    EditText save;
    Spinner spinner;

    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText, String sort, Boolean sports, Boolean arts, Boolean fashion);
    }

    //THIS IS WHERE WE WANT TO GET ALL VALUES
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            Log.d("DEBUG", "Success CLick");
            // Return input text back to activity through the implemented listener
            EditNameDialogListener listener = (EditNameDialogListener) getActivity();
            // Close the dialog and return back to the parent activity


            date = (EditText) v.findViewById(R.id.etStartDate);
            String the_date = date.getText().toString();

            spinner = (Spinner) v.findViewById(R.id.spSort);
            String sort = spinner.getSelectedItem().toString();

            CheckBox checkFashion = (CheckBox) v.findViewById(R.id.cbFashion);
            Boolean fashion = checkFashion.isChecked();

            CheckBox checkArts = (CheckBox) v.findViewById(R.id.cbArts);
            Boolean arts = checkArts.isChecked();

            CheckBox checkSports = (CheckBox) v.findViewById(R.id.cbSports);
            Boolean sports = checkSports.isChecked();

            listener.onFinishEditDialog(the_date,  sort,  sports,  arts,  fashion);

            dismiss();
            return true;
        }
        Log.d("DEBUG", Integer.toString(actionId));
        return false;
    }


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

        save = (EditText) view.findViewById(R.id.btnFilter);
        date = (EditText) view.findViewById(R.id.etStartDate);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(FilterFragment.this, 300);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        save.setOnEditorActionListener(this);
    }

    public void setDate(Calendar c) {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
        date.setText(format.format(c.getTime()));
    }




}
