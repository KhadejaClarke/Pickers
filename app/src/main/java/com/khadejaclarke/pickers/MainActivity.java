package com.khadejaclarke.pickers;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button btnDatePicker, btnTimePicker, btnReset;
    Spinner spinner_activities;
    int mYear, mMonth, mDay, mHour, mMinute;
    String selectedActivity = "";
    String selectedDate = "";
    String selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtnReset();
        initSpinner();
        initBtnDatePicker();
        initBtnTimePicker();

    }

    private void initBtnReset() {
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void initSpinner() {
        spinner_activities = findViewById(R.id.spinner_activities);
        spinner_activities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedActivity = (String) parent.getItemAtPosition(position);
                updateBtnClear();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initBtnDatePicker() {
        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        btnDatePicker.setText(selectedDate);
                        updateBtnClear();
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });
    }

    private void initBtnTimePicker() {
        btnTimePicker = findViewById(R.id.btnTimePicker);
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute =
                        c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String shour = Integer.toString(hourOfDay);
                        String smin = Integer.toString(minute);

                        // Add 0 to clock for single digits
                        if (shour.length() < 2){
                            shour = "0" + shour;
                        }

                        if (smin.length() < 2){
                            smin = "0" + smin;
                        }

                        selectedTime = shour + ":" + smin;

                        btnTimePicker.setText(selectedTime);

                        updateBtnClear();

                    }
                }, mHour, mMinute, false);

                timePickerDialog.show();
            }
        });
    }

    private void updateBtnClear() {
        runOnUiThread(new Runnable() {
            public void run() {
                String date_btn_label = btnDatePicker.getText().toString();
                String time_btn_label = btnTimePicker.getText().toString();

                if (selectedActivity.equals("SELECT AN ACTIVITY") || date_btn_label.equals("SELECT DATE") || time_btn_label.equals("SELECT TIME"))
                    btnReset.setVisibility(View.GONE);
                else
                    btnReset.setVisibility(View.VISIBLE);
            }
        });
    }

    private void reset() {
        String[] entries = getResources().getStringArray(R.array.activities);
        spinner_activities.setSelection(Arrays.asList(entries).indexOf("SELECT AN ACTIVITY"));

        btnDatePicker.setText("SELECT DATE");

        btnTimePicker.setText("SELECT TIME");
    }
}
