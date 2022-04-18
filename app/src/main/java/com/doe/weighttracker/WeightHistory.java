/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import static java.lang.Math.pow;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class WeightHistory {
    private static final int MAX_DAYS = 365;
    private ArrayList<WeightData> history;
    private Context parent;
    private String fileName;

    public WeightHistory(Context context) {
        parent = context;
        history = new ArrayList<>(MAX_DAYS);
        fileName = parent.getFilesDir()+"/myWHistory.txt";
        File fin = new File(fileName);
        if (fin.exists()) history = readFromFile(fileName);
        else {
            try {
                fin.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<WeightData> readFromFile(String fileName) {
        try {
            FileInputStream fin = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    class WeightData {
        private double weight;
        private double height;
        private Date date;
        private double bmi;
        private WeightStatus weightStatus;

        public WeightData(double weight, Date date) {
            this.weight = weight;
            this.date = date;
            bmi = (weight /pow(height,2) ) * 703;
            setBMIStatus();
        }

        private void setBMIStatus(){
            if (bmi<=18.5) weightStatus = WeightStatus.UNDER;
            else if (bmi<25) weightStatus =  WeightStatus.HEALTHY;
            else if (bmi<30) weightStatus =  WeightStatus.OVER;
            else weightStatus = WeightStatus.OBESE;
        }


    }
    enum WeightStatus {UNDER,HEALTHY,OVER,OBESE}
}
