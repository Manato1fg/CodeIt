package it.code.sortvisualization.sort;

import java.util.ArrayList;

import it.code.sortvisualization.Main;

public class StalinSort extends Sort {

    public static final double NULL = -1.0;

    public StalinSort(Main main) {
        super(main);
    }

    int i = 0;
    double last = -1.0;

    @Override
    public void step() {
        if(i == this.data.size()) {
            this.finish();
            return;
        }

        if(last > this.data.get(i)) {
            this.data.set(i, NULL);
            i++;
        } else {
            last = this.data.get(i);
            i++;
        }
    }
}