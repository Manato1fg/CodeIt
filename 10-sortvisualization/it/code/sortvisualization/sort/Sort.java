package it.code.sortvisualization.sort;

import java.util.ArrayList;
import it.code.sortvisualization.*;

public class Sort {
    public ArrayList<Double> data = new ArrayList<>();
    public int count;

    public Sort(int count) {
        this.count = count;
        Main.init(this.data);
    }

    public Sort(ArrayList<Double> data) {
        this.count = data.size();
        this.data = data;
    }

    public ArrayList<Double> getData() {
        return this.data;
    }

    public void step() {

    }
}