package ch.ost.mge.todo.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class DatePickerFragment extends DialogFragment {
    private Date _dateTimeDue;

    public DatePickerFragment(Date dateTimeDue) {
        _dateTimeDue = dateTimeDue;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = _dateTimeDue.getYear()+1900;
        int month = _dateTimeDue.getMonth();
        int day = _dateTimeDue.getDate();

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
    }
}