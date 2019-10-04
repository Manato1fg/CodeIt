package it.code.dftdrawing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Data {

    public static final String FILE_STRING = "./Data.txt";

    public static void get(ArrayList<Double> x_data, ArrayList<Double> y_data, int skipCount, double scaler) throws IOException{
        File file = new File(FILE_STRING);
        BufferedReader bReader = new BufferedReader(new FileReader(file));
        String line;
        int i = 0;
        while((line = bReader.readLine()) != null) {
            i++;
            if(i % skipCount != 0) continue;
            String[] xy = line.split(",");
            x_data.add(Double.valueOf(xy[0]) * scaler);
            y_data.add(Double.valueOf(xy[1]) * scaler);
        }
        bReader.close();
    }
}