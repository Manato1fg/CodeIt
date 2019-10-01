//Automatically Created by CodeIt Setup
package it.code.dft;

import com.mnt1fg.moonlit.*;

import it.code.dft.DFT.FourierData;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Main implements MoonlitInterface {
    
    private static final int WIDTH = 800;
    private static final int HEIGHT = 700;

    public static final int data_length = 50;
    FourierData[] fdArray;
    public static final double dt = 0.002;
    public static double t = 0.00;
    ArrayList<Double> y_data = new ArrayList<>();


    public static void main(String[] args) { 
        new Main();
    }

    public Main() {

        double[] x = Moonlit.createRandomArray(data_length, 0.0, 150.0);
        fdArray = DFT.dft(x);
        Moonlit moonlit = Moonlit.getInstance();

        moonlit.createWindow(WIDTH, HEIGHT);
        moonlit.setBackgroundColor(Color.black);
        moonlit.setTicks(20);
        moonlit.register(this);
        moonlit.showWindow();
    }

    public void epiCycles(Graphics g, int offsetX, int offsetY, FourierData[] data) {
        double x = offsetX;
        double y = offsetY;
        double x1, y1;
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.white);
        for (int i = 0; i < data_length; i++) {
            moonlit.drawCircle(g, (int) x, (int) y, (int) data[i].cn.amp);
            x1 = x + data[i].cn.amp * Math.cos(data[i].frequency * t + data[i].cn.angle);
            y1 = y + data[i].cn.amp * Math.sin(data[i].frequency * t + data[i].cn.angle);
            if(i != data.length - 1)
                moonlit.drawLine(g, (int) x, (int) y, (int) x1, (int) y1);
            x = x1;
            y = y1;
        }
        int dx = 2;
        int ex = 450;
        moonlit.drawLine(g, (int) x, (int) y, (int) ex, (int) y);
        y_data.add(y);
        if(y_data.size() > ex / dx) {
            y_data.remove(0);
        } 
        for(int i = y_data.size() - 1; i > 0; i--) {
            moonlit.drawLine(g, ex, y_data.get(i - 1).intValue(), ex + dx, (int) y_data.get(i).doubleValue());
            ex += dx;
        }
    }

    @Override
    public void onUpdate(Graphics g) { 
        t += dt;
        epiCycles(g, 200, HEIGHT / 2, fdArray);
    }
}