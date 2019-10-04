//Automatically Created by CodeIt Setup
package it.code.dftdrawing;

import com.mnt1fg.moonlit.*;

import it.code.dftdrawing.DFT.FourierData;

import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;

public class Main implements MoonlitInterface {

    FourierData[] x_fd;
    FourierData[] y_fd;
    public static double t = 0.00;

    ArrayList<MVector> points = new ArrayList<>();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(600, 600);
        ArrayList<Double> x_data = new ArrayList<>();
        ArrayList<Double> y_data = new ArrayList<>();
        try {
            Data.get(x_data, y_data, 10, 3.0);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        x_fd = DFT.dft(x_data);
        y_fd = DFT.dft(y_data);

        moonlit.setBackgroundColor(Color.black);
        moonlit.setTicks(1);
        moonlit.register(this);
        moonlit.showWindow();
    }

    public MVector epiCycles(Graphics g, int offsetX, int offsetY, double phi, FourierData[] data) {
        double x = offsetX;
        double y = offsetY;
        double x1, y1;
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.white);
        int data_length = data.length;
        for (int i = 0; i < data_length; i++) {
            moonlit.drawCircle(g, (int) x, (int) y, (int) data[i].cn.amp);
            x1 = x + data[i].cn.amp * Math.cos(data[i].frequency * t + data[i].cn.angle + phi);
            y1 = y + data[i].cn.amp * Math.sin(data[i].frequency * t + data[i].cn.angle + phi);
            if (i != data.length - 1)
                moonlit.drawLine(g, (int) x, (int) y, (int) x1, (int) y1);
            x = x1;
            y = y1;
        }
        return new MVector(x, y);
    }

    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.black);
        moonlit.fillRect(g, 0, 0, 600, 600);
        moonlit.setStroke(g, 1);
        MVector v1 = epiCycles(g, 300, 40, 0.0, x_fd);
        MVector v2 = epiCycles(g, 40, 300, Math.PI / 2, y_fd);
        // Moonlit.log("V1: " + (v1.x - 400) + " , " + (v1.y - 40));
        moonlit.setColor(g, Color.white);
        moonlit.drawLine(g, v1.x, v1.y, v1.x, v2.y);
        moonlit.drawLine(g, v2.x, v2.y, v1.x, v2.y);
        MVector newPoint = new MVector(v1.x, v2.y);
        points.add(newPoint);

        int data_max = x_fd.length - 1;
        if(points.size() > data_max) {
            points.remove(0);
        }
        moonlit.setStroke(g, 4);
        for (int i = points.size() - 1; i > 0; i--) {
            MVector p1 = points.get(i);
            MVector p2 = points.get(i - 1);
            moonlit.drawLine(g, p1.x, p1.y, p2.x, p2.y);
        }
        double dt = 2 * Math.PI / (double) x_fd.length;
        t += dt;
        if (t > 2 * Math.PI) {
            t = 0;
        }
    }
}