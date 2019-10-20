package it.code.sortvisualization.sort;

import java.util.ArrayList;

import com.mnt1fg.moonlit.Moonlit;

import it.code.sortvisualization.*;

public class Sort {
    public ArrayList<Double> data = new ArrayList<>();
    public int count;
    public Main main;

    public Sort(Main main) {
        main.init(this.data);
        this.main = main;
    }

    public Sort(ArrayList<Double> data) {
        this.data = data;
    }

    public ArrayList<Double> getData() {
        return this.data;
    }

    public void step() {

    }

    public void finish() {
        main.finished = true;
    }
}