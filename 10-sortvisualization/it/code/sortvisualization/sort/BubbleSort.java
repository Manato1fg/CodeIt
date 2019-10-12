package it.code.sortvisualization.sort;

import java.util.ArrayList;

public class BubbleSort extends Sort {
    public BubbleSort(int count) {
        super(count);
    }

    public BubbleSort(ArrayList<Double> data) {
        super(data);
    }

    int lastIndex = 0;
    int n = 0;
    public void step() {
        if (n == this.count - 1)
            return;

        double a = 0.0, b = 0.0;
        while(true) {
            if (lastIndex == this.count - n - 1) {
                n++;
                if (n == this.count - 1) {
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
                break;
            }
            lastIndex++;
        }
    }
}