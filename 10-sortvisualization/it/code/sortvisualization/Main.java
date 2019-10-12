//Automatically Created by CodeIt Setup
package it.code.sortvisualization;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;
import it.code.sortvisualization.sort.*;

public class Main implements MoonlitInterface {
    ArrayList<Integer> changed = new ArrayList<>();
    int initial = 0;

    public static final int BUBBLE_SORT = 0;
    public static final int QUICK_SORT = 1;
    public static final int STALIN_SORT = 2;
    public int sort_type = STALIN_SORT;
    public Sort sort;

    public static final int count = 240;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(800, 600);
        moonlit.setBackgroundColor(Color.black);
        moonlit.setTicks(100);
        //moonlit.noLoop = true;
        moonlit.register(this);
        moonlit.translate(400, 300);
        moonlit.showWindow();
        switch(sort_type) {
            case BUBBLE_SORT:
                sort = new BubbleSort(count);
                break;
            case QUICK_SORT:
                sort = new QuickSort(count);
                break;
            case STALIN_SORT:
                sort = new StalinSort(count);
                break;
            default:
                sort = new BubbleSort(count);
        }
    }

    public static void init(ArrayList<Double> data) {
        double step = 1.0 / (double) count;
        for (int i = 0; i < count; i++) {
            data.add(step * (double) i);
        }
        shuffle(data);
    }

    public static void shuffle(ArrayList<Double> _data) {
        double a = 0.0;
        for (int i = 0; i < count; i++) {
            int index = (int) Math.floor(Math.random() * count);
            a = _data.get(index);
            _data.set(index, _data.get(i));
            _data.set(i, a);
        }
    }

    void drawPoint(int i, Graphics g) {
        double radius = 200;
        Moonlit moonlit = Moonlit.getInstance();
        ArrayList<Double> data = sort.getData();
        double a = data.get(i);
        if(a == StalinSort.NULL) return;
        double b = (double) i / (double) count;
        Color c = Color.getHSBColor((float) a, 1.0f, 1.0f);
        moonlit.setColor(g, c);
        double r = Moonlit.map(Math.abs(a * count - i), 0.0, count, radius, 0.0);
        moonlit.fillCircle(g, r * Math.cos(2 * Math.PI * b), r * Math.sin(2 * Math.PI * b), 2);
    }

    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.black);
        moonlit.fillRect(g, -400, -300, 800, 600);
        for(int i = 0; i < count; i++) { 
            drawPoint(i, g);
        } 

        sort.step();
    }
    
}