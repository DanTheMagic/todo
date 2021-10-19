package ch.ost.mge.todo.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Date;

public class TimePickerFragment extends DialogFragment {
    private Date _dateTimeDue;

    public TimePickerFragment(Date dateTimeDue) {
        _dateTimeDue = dateTimeDue;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour = _dateTimeDue.getHours();
        int minute = _dateTimeDue.getMinutes();

        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
}
