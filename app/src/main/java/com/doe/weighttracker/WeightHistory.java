/**
 * @author Deniz Erisgen ©
 **/
package com.doe.weighttracker;

import static java.lang.Math.pow;
import android.content.Context;
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
        ArrayList<WeightData> readFromFile = new ArrayList<>(MAX_DAYS);
        // TODO: 4/17/22 read lines for size and create indv. weight data
        try {
            FileInputStream fin = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readFromFile;
    }

    class WeightData {
        private double weight;
        private double height;
        private Date date;
        private double bmi;
        private BMIStatus BMIStatus;

        public WeightData(double weight, Date date) {
            this.weight = weight;
            this.date = date;
            bmi = (weight /pow(height,2) ) * 703;
            setBMIStatus();
        }

        private void setBMIStatus(){
            if (bmi<=18.5) BMIStatus = WeightHistory.BMIStatus.UNDER;
            else if (bmi<25) BMIStatus = WeightHistory.BMIStatus.HEALTHY;
            else if (bmi<30) BMIStatus = WeightHistory.BMIStatus.OVER;
            else BMIStatus = WeightHistory.BMIStatus.OBESE;
        }

    }
    enum BMIStatus {UNDER,HEALTHY,OVER,OBESE}
}
