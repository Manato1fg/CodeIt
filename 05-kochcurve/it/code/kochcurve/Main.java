//Automatically Created by CodeIt Setup
package it.code.kochcurve;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Function;
import java.awt.Color;

public class Main implements MoonlitInterface {

    int width = 600, height = 640;
    boolean flag = false;

    public static final double R3 = 1.7320508;
    public static final int KEY_SPACE = 32;

    ArrayList<Point> points = new ArrayList<>();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        double w = width - 100.0;
        points.add(new Point((width - w) / 2, (w / 6) * R3 + 20));
        points.add(new Point(width - (width - w) / 2, (w / 6) * R3 + 20));
        points.add(new Point(width / 2, (w / 6) * R3 + 20 + w / 2 * R3));
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(width, height);
        moonlit.setBackgroundColor(Color.black);
        moonlit.setTicks(0.05);
        moonlit.register(this);
        moonlit.onKeyPressed(fn);
        moonlit.showWindow();
    }

    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        int size = points.size();
        for (int i = 0; i < size - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            moonlit.setStroke(g, 1);
            moonlit.setColor(g, 0, 255, 0);
            moonlit.drawLine(g, (int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
        }
        Point p1 = points.get(points.size() - 1);
        Point p2 = points.get(0);
        moonlit.setStroke(g, 1);
        moonlit.setColor(g, 0, 255, 0);
        moonlit.drawLine(g, (int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
    }

    Function<KeyEvent, Void> fn = (e) -> {
        if (e.getKeyCode() == KEY_SPACE) {
            ArrayList<Point> newPoints = new ArrayList<>();
            int size = points.size();
            for (int i = 0; i < size; i++) {
                Point p1, p2;
                if (i == size - 1) {
                    p1 = points.get(i);
                    p2 = points.get(0);
                } else {
                    p1 = points.get(i);
                    p2 = points.get(i + 1);
                }

                Point p3 = new Point((p1.x * 2 + p2.x * 1) / 3.0, (p1.y * 2 + p2.y * 1) / 3.0);
                Point p4 = new Point((p1.x * 1 + p2.x * 2) / 3.0, (p1.y * 1 + p2.y * 2) / 3.0);

                double tan = (p3.y - p4.y) / (p3.x - p4.x);
                double l = p3.distance(p4) / 2;
                double h = -l * R3;
                Point p5;
                double x = l;
                double y = h;
                if (tan == 0) {
                    if (p3.x < p4.x)
                        p5 = new Point(x + p3.x, y + p3.y);
                    else
                        p5 = new Point(-x + p3.x, -y + p3.y);

                } else {
                    double cos = Math.sqrt(1 / (1 + tan * tan));
                    double sin = Math.sqrt(1 - cos * cos);
                    if (p3.x > p4.x)
                        cos *= -1;
                    if (p3.y > p4.y)
                        sin *= -1;

                    double xx, yy;
                    xx = x * cos - y * sin;
                    yy = x * sin + y * cos;
                    p5 = new Point(xx + p3.x, yy + p3.y);
                }
                newPoints.add(p1);
                newPoints.add(p3);
                newPoints.add(p5);
                newPoints.add(p4);
            }
            points = newPoints;
            Moonlit.getInstance().repaint();
        }
        return null;
    };

    class Point {

        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Point p) {
            return Math.sqrt((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y));
        }
    }
}
