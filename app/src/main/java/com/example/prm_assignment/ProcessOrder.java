package com.example.prm_assignment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ProcessOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_order_row);

    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        month = month + 1;
        String date = year + "-" + month + "-" + day;
        if (month < 10) {
            date = year + "-" + "0" + month + "-" + day;
        }
        if (day < 10) {
            date = year + "-" + month + "-" + "0" + day;
        }
        if (month < 10 && day < 10) {
            date = year + "-" + "0" + month + "-" + "0" + day;
        }
        return date;
    }

}