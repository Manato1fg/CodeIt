package it.code.sortvisualization.sort;

import java.util.ArrayList;

import com.mnt1fg.moonlit.Moonlit;

import it.code.sortvisualization.Main;

public class HeapSort extends Sort {

    public HeapSort(Main main) {
        super(main);
    }

    int n = 0;

    @Override
    public void step() {
        if (n == Main.count) {
            this.finish();
            return;
        }
        upheap();
        n++;
    }

    public void upheap() {
        for (int i = 1; i < this.data.size() - n; i++) {
            int parentIndex = (int) Math.floor((i - 1) / 2);
            double parent = this.data.get(parentIndex);
            int childIndex = i;
            double child = this.data.get(childIndex);
            while (parentIndex >= 0) {
                if (parent < child) {
                    this.data.set(childIndex, parent);
                    this.data.set(parentIndex, child);
                    childIndex = parentIndex;
                    parentIndex = (int) Math.floor((parentIndex - 1) / 2);
                    parent = this.data.get(parentIndex);
                } else
                    break;
            }
        }
        
        double last = this.data.get(this.data.size() - 1 - n);
        double first = this.data.get(0);
        this.data.set(0, last);
        this.data.set(this.data.size() - 1 - n, first);
    }

}