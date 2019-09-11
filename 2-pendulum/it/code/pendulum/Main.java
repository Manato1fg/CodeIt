//Automatically Created by CodeIt Setup
package it.code.pendulum;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Main implements MoonlitInterface { 

    public static final double gravity = 9.80665;
    public static final int ticks = 20;

    public ArrayList<Pendulum> pendulums = new ArrayList<Pendulum>();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(800, 600);
        moonlit.setBackgroundColor(new Color(200,200,200));
        moonlit.setTicks(this.ticks);
        moonlit.register(this);
        
        for(int i = 0; i < 50; i++) {
            this.pendulums.add(new Pendulum(400, 200, Math.toRadians(40.0), 100+i));
        }

        moonlit.setPlaySpeed(100);

        moonlit.showWindow();
    }

    @Override
    public void onUpdate(Graphics g) {
        this.pendulums.forEach(p -> {
            p.draw(g);
        });
    }

    private class Pendulum {
        private int x, y;
        private double length, omega, theta;
        private MVector v = new MVector(0.0, 0.0);
        public static final double MASS = 1.0;
        double h;//microtime
        public Pendulum(int x, int y, double theta0, double length) {
            this.x = x;
            this.y = y;
            this.length = length;
            this.omega = -0.20;
            this.theta = theta0;
            h = 1.0 / (double) Main.ticks;
        }

        public void draw(Graphics g) {
            Moonlit moonlit = Moonlit.getInstance();

            /**
             * update parameters by Runge–Kutta method
             */
            double k1, k2, k3, k4, l1, l2, l3, l4, k, l;
            k1 = F(moonlit.elapsedTime, this.omega, this.theta);
            l1 = G(moonlit.elapsedTime, this.omega, this.theta);
            k2 = F(moonlit.elapsedTime + h / 2, this.omega + h / 2 * k1, this.theta + h / 2 * l1);
            l2 = G(moonlit.elapsedTime + h / 2, this.omega + h / 2 * k1, this.theta + h / 2 * l1);
            k3 = F(moonlit.elapsedTime + h / 2, this.omega + h / 2 * k2, this.theta + h / 2 * l2);
            l3 = G(moonlit.elapsedTime + h / 2, this.omega + h / 2 * k2, this.theta + h / 2 * l2);
            k4 = F(moonlit.elapsedTime + h, this.omega + h * k3, this.theta + h * l3);
            l4 = G(moonlit.elapsedTime + h, this.omega + h * k3, this.theta + h * l3);
            k = h * (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6;
            l = h * (l1 + 2.0 * l2 + 2.0 * l3 + l4) / 6;

            this.omega += k;
            this.theta += l;

            double bx = this.length * Math.sin(this.theta);
            double by = this.length * Math.cos(this.theta);
            moonlit.setColor(g, Color.red);
            moonlit.fillCircle(g, (int) bx + x, (int) (y + by), 4);
            moonlit.setColor(g, new Color(0, 0, 0));
            moonlit.drawLine(g, this.x, this.y, (int) bx + x, (int) (y + by));
        }

        public double F(double t, double omega, double theta) {
            return - gravity / this.length *  Math.sin(theta);
        }

        public double G(double t, double omega, double theta) {
            return omega;
        }
    }
}