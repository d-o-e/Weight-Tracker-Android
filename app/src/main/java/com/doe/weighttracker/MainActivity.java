/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editWeight;
    private EditText editHeight;
    private WeightHistory history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editHeight = findViewById(R.id.enterHeightTextView);
        editWeight = findViewById(R.id.enterWeightTextView);
        TextView heightText = findViewById(R.id.mainHeightTextView);
        Button addData = findViewById(R.id.addDataButtonView);
        Button showHistory = findViewById(R.id.showHistoryButtonView);

        history = new WeightHistory(this);
        if (history.getHeight() == 0) {
            heightText.setEnabled(false);
            heightText.setVisibility(View.GONE);
            editHeight.setEnabled(true);
            editHeight.setVisibility(View.VISIBLE);
        } else {
            heightText.setEnabled(true);
            heightText.setVisibility(View.VISIBLE);
            heightText.setText(heightText.getText().toString() +
                    history.getHeight() + " in");
            editHeight.setEnabled(false);
            editHeight.setVisibility(View.GONE);

        }

        addData.setOnClickListener(view -> {
            double weight = Double.parseDouble(editWeight.getText().toString());
            if (editHeight.isEnabled()) {
                double height = Double.parseDouble(editHeight.getText().toString());
                history.initHistoryFile(weight,height);
            } else history.addToHistoryFile(weight,false);
        });
        showHistory.setOnClickListener(this::showHistory);

    }

    /**
     * Shows Video Player Activity
     */
    private void showHistory(View view) {
        Intent intent = new Intent(this, HistoryViewer.class);
        startActivity(intent);
    }

}