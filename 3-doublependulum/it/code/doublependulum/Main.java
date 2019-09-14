//Automatically Created by CodeIt Setup
package it.code.doublependulum;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.awt.Color;

public class Main implements MoonlitInterface { 

    public static final double g = 9.80665;
    public static final double l1 = 50.0;
    public static final double l2 = 50.0;
    public static final double m1 = 3.0;
    public static final double m2 = 1.0;
    public static final int r = 1;

    public static final int s = 3;

    public static int ticks = 10;
    public static double dt = 1.0 / ticks;

    public static final Color ballColor = new Color(255, 255, 255);
    public static final Color lineColor = new Color(200, 200, 200); 

    private double theta1, theta2;
    private double omega1, omega2;

    private static int width = 1366, height = 768;

    private int lastX, lastY;

    public static void main(String[] args) { 
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(width, height);
        moonlit.setTitle("#3 Double Pendulum");
        moonlit.setBackgroundColor(new Color(0, 0, 0));
        moonlit.setTicks(ticks);
        moonlit.register(this);
        this.init();
        moonlit.showWindow();
    }

    private void init(){
        this.theta1 = Math.toRadians(45);
        this.theta2 = Math.toRadians(150);
        this.omega1 = 0.0;
        this.omega2 = 0.0;
        int[] XY = translate(I(l1 * S(this.theta1) + l2 * S(this.theta2)), I(l1 * C(this.theta1) + l2 * C(this.theta2)));
        this.lastX = XY[0];
        this.lastY = XY[1];
    }

    @Override
    public void onUpdate(Graphics g) {
        /**
         * ルンゲクッタ法による更新
         */
        double[] f1, f2, f3, f4;
        double a, b;
        double dt2 = dt / 2;
        f1 = F(
            this.theta1, 
            this.theta2, 
            this.omega1, 
            this.omega2
            );
        f2 = F(
            this.theta1 + f1[0] * dt * dt2, 
            this.theta2 + f1[1] * dt * dt2, 
            this.omega1 + f1[0] * dt2,
            this.omega2 + f1[1] * dt2
            );
        f3 = F(
            this.theta1 + f2[0] * dt * dt2, 
            this.theta2 + f2[1] * dt * dt2, 
            this.omega1 + f2[0] * dt2,
            this.omega2 + f2[1] * dt2
            );
        f4 = F(
            this.theta1 + f3[0] * dt, 
            this.theta2 + f3[1] * dt, 
            this.omega1 + f3[0],
            this.omega2 + f3[1]
            );
        a = (f1[0] + 2 * f2[0] + 2 * f3[0] + f4[0]) / 6 * dt;
        b = (f1[1] + 2 * f2[1] + 2 * f3[1] + f4[1]) / 6 * dt;

        this.omega1 += a;
        this.omega2 += b;

        this.theta1 += this.omega1 * dt;
        this.theta2 += this.omega2 * dt;

        /**
         * 描画
         */

        int[] xy = translate(s * I(l1 * S(this.theta1)), s * I(l1 * C(this.theta1)));
        int[] XY = translate(s * I(l1 * S(this.theta1) + l2 * S(this.theta2)), s * I(l1 * C(this.theta1) + l2 * C(this.theta2)));

        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, lineColor);
        moonlit.setStroke(g, 3);
        moonlit.drawLine(g, width / 2, height / 2, xy[0], xy[1]);
        moonlit.drawLine(g, xy[0], xy[1], XY[0], XY[1]);
        moonlit.setColor(g, ballColor);
        moonlit.fillCircle(g, xy[0], xy[1], 2);
        moonlit.fillCircle(g, XY[0], XY[1], 2);
        //moonlit.setColor(g, Color.red);

    }

    private static int[] translate(int x, int y) {
        return new int[]{x + width / 2, y + height / 2};
    }

    public static int I(double a){
        return (int) a;
    }

    public static double C(double a) {
        return Math.cos(a);
    }

    public static double S(double a) {
        return Math.sin(a);
    }

    /**
     * t1 : θ1 
     * t2 : θ2
     */
    public static double[] F(double t1, double t2, double o1, double o2) {
        double a = C(t1) * C(t2) + S(t1) * S(t2); //cos(t1 - t2)
        double b = S(t1) * C(t2) - C(t1) * S(t2); //sin(t1 - t2)
        double M = m1 + m2;

        double c = g * (S(t2) * a - M * S(t1) / m2) - (l1 * o1 * o1 * a + l2 * o2 * o2) * b;
        double d = l1 * (M / m2 - a * a);
        
        double e = g * M / m2 * (a * S(t1) - S(t2)) + (M / m2 * l1 * o1 * o1 + l2 * o2 * o2 * a) * b;
        double f = l2 * (M / m2 - a * a);
        return new double[]{c / d, e / f};
    }

}