/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText editWeight;
    private EditText editHeight;
    private Button addData;
    private Button showHistory;

    WeightHistory history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        history = new WeightHistory(this);
        addData.setOnClickListener(view -> {
            // TODO: 4/18/2022 save the data from editTexts above
        });
        showHistory.setOnClickListener(view -> {
            // TODO: 4/18/2022 transition to showHistory activity
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        history.save();
    }


}