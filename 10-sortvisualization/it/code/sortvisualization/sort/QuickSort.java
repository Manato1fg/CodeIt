package it.code.sortvisualization.sort;

import java.util.ArrayList;

import it.code.sortvisualization.Main;

public class QuickSort extends Sort{
    public ArrayList<Pair> next = new ArrayList<>();
    public Pair now;
    public boolean first = true;
    public boolean update = true;

    public QuickSort(Main main) {
       super(main);
    }

    int i = 0, j = 0;
    int left = 0, right = 0;
    double pivot;

    BubbleSort bs = null;

    @Override
    public void step() {
        if(first) {
            next.add(new Pair(0, Main.count - 1));
            first = false;
        }

        if(update) {
            if(next.size() == 0) {
                this.finish();
                return;
            }
            this.update = false;
            now = next.get(0);
            next.remove(0);
            left = now.left;
            right = now.right;
            i = left;
            j = right;
            pivot = data.get((i + j) / 2);
        }

        if(left < right) {
            while(data.get(i) < pivot) i++;
            while(data.get(j) > pivot) j--;
            if(i >= j) {
                this.update = true;
                this.updateNext(left, right, i, j);
                return;
            }

            double tmp = data.get(i);
            data.set(i, data.get(j));
            data.set(j, tmp);
            i++; j--;

        } else {
            this.update = true;
            this.updateNext(left, right, i, j);
            return;
        }
    }

    public void updateNext(int left, int right, int i, int j){
        if( i - 1 - left >= 0) {
            next.add(new Pair(left, i - 1));
        }

        if (right - j - 1 >= 0) {
            next.add(new Pair(j + 1, right));
        }
        return;
    }

    public static class Pair {
        int left, right;
        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
}