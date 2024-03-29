package it.code.sortvisualization.sort;

import java.util.ArrayList;

import it.code.sortvisualization.Main;

public class BubbleSort extends Sort {
    public BubbleSort(Main main) {
        super(main);
    }

    public BubbleSort(ArrayList<Double> data) {
        super(data);
    }

    int lastIndex = 0;
    int n = 0;
    public void step() {
        if (n == Main.count - 1){
            this.finish();
            return;
        }

        double a = 0.0, b = 0.0;
        while(true) {
            if (lastIndex == Main.count - n - 1) {
                n++;
                if (n == Main.count - 1) {
                    return;
                }
                lastIndex = 0;
                return;
            }
            a = data.get(lastIndex);
            b = data.get(lastIndex + 1);
            if (a > b) {
                data.set(lastIndex, b);
                data.set(lastIndex + 1, a);
            }
            lastIndex++;
        }
    }
}