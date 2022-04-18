/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeightHistory history = new WeightHistory(this);
    }

    private double heightInterpret(String height) {

    }
}