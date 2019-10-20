package it.code.sortvisualization.sort;

import java.util.ArrayList;

import com.mnt1fg.moonlit.Moonlit;

import it.code.sortvisualization.Main;
import it.code.sortvisualization.sort.QuickSort.Pair;

public class MergeSort extends Sort {
    ArrayList<Pair> next = new ArrayList<>();
    ArrayList<ArrayListWithPosition> left = new ArrayList<>();
    ArrayList<ArrayListWithPosition> right = new ArrayList<>();

    int separateCount = 0;
    int num = 1;
    int lastX = 0;
    public MergeSort(Main main) {
        super(main);
    }

    
    @Override
    public void step() {
        if(lastX + num >= Main.count) {
            this.finish();
            return;
        }
        if(lastX + 2 * num >= Main.count) {
            ArrayListWithPosition a = getSeparetedArrayList(lastX, num, this.data);
            lastX += num;
            ArrayListWithPosition b = getSeparetedArrayList(lastX, this.data.size() - lastX, this.data);
            merge(a, b, this.data);
            lastX = 0;
            num *= 2;
            return;
        }
        ArrayListWithPosition a = getSeparetedArrayList(lastX, num, this.data);
        lastX += num;
        ArrayListWithPosition b = getSeparetedArrayList(lastX, num, this.data);
        lastX += num;
        merge(a, b, this.data);
    }

    public ArrayListWithPosition getSeparetedArrayList(int left, int count, ArrayList<Double> original) {
        ArrayList<Double> a = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            a.add(original.get(i + left));
        }
        ArrayListWithPosition alwp = new ArrayListWithPosition(left, a);
        return alwp;
    }

    public void merge(ArrayListWithPosition aalwp, ArrayListWithPosition balwp, ArrayList<Double> merged) {
        ArrayList<Double> a = aalwp.data, b = balwp.data;
        int l = aalwp.x;
        double aa, bb;
        while(a.size() != 0 || b.size() != 0) {
            if(a.size() == 0) {
                merged.set(l, b.get(0));
                b.remove(0);
                l++;
                continue;
            }

            if (b.size() == 0) {
                merged.set(l, a.get(0));
                a.remove(0);
                l++;
                continue;
            }

            aa = a.get(0);
            bb = b.get(0);
            if(aa <= bb) {
                merged.set(l, aa);
                a.remove(0);
                l++;
                continue;
            }

            if(aa > bb) {
                merged.set(l, bb);
                b.remove(0);
                l++;
                continue;
            }
        }
    }
    class ArrayListWithPosition {
        int x;
        ArrayList<Double> data;

        public ArrayListWithPosition(int x, ArrayList<Double> data) {
            this.x = x;
            this.data = data;
        }
    }
}