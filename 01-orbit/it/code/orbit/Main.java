//Automatically Created by CodeIt Setup
package it.code.orbit;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Main implements MoonlitInterface {

    /**
     * sun radius 695,510 km the earth radius 6,371 km the earth orbit radius
     * 149,600,000 km mars radius 3389.5 km mars orbit radius 227,939,200 km
     */

    public static final double SUN_RADIUS = 695510.0;
    public static final double MERCURY_RADIUS = 2439.7 * 5.0;
    public static final double MERCURY_ORBIT_RADIUS = 57910000.0;
    public static final double VENUS_RADIUS = 6051.8 * 5.0;
    public static final double VENUS_ORBIT_RADIUS = 108208000.0;
    public static final double EARTH_RADIUS = 6371.0 * 5.0;
    public static final double EARTH_ORBIT_RADIUS = 149600000.0;
    public static final double MARS_RADIUS = 3389.5 * 5.0;
    public static final double MARS_ORBIT_RADIUS = 227939200.0;
    public static final double JUPITER_RADIUS = 69911.0;
    public static final double JUPITER_ORBIT_RADIUS = 778500000.0;
    public static final double SATURN_RADIUS = 58232.0;
    public static final double SATURN_ORBIT_RADIUS = 1426725400;

    public static final double MERCURY_OMEGA = 4.096 * 20.0;// 360[degrees] / 87.9[days] = 4.096[degrees/days];
    public static final double VENUS_OMEGA = 1.602 * 20.0;// 360[degrees] / 224.7[days] = 1.602[degrees/days];
    public static final double EARTH_OMEGA = 0.986 * 20.0; // 360[degrees] / 365[days] = 0.986[degrees/days];
    public static final double MARS_OMEGA = 0.524 * 20.0; // 360[degrees] / 686.971[days] = 0.524[degrees/days];
    public static final double JUPITER_OMEGA = 0.08316 * 20.0; // 360[degrees] / 365[days] / 11.86[years] = 0.08316[degrees/days*years];
    public static final double SATURN_OMEGA = 0.03401 * 20.0; // 360[degrees] / 365[days] / 29[years] = 0.03401[degrees/days*years];

    private ArrayList<Planet> planets = new ArrayList<Planet>();

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(900, 700);
        moonlit.setBackgroundColor(new Color(5, 0, 28));
        moonlit.setTicks(60);
        moonlit.register(this);
        Planet mercury = new Planet("mercury", MERCURY_RADIUS, MERCURY_ORBIT_RADIUS, MERCURY_OMEGA,
                new Color(0, 208, 255));
        Planet venus = new Planet("venus", VENUS_RADIUS, VENUS_ORBIT_RADIUS, VENUS_OMEGA, new Color(233, 232, 255));
        Planet earth = new Planet("earth", EARTH_RADIUS, EARTH_ORBIT_RADIUS, EARTH_OMEGA, new Color(0, 0, 255));
        Planet mars = new Planet("mars", MARS_RADIUS, MARS_ORBIT_RADIUS, MARS_OMEGA, new Color(252, 86, 3));
        Planet jupiter = new Planet("jupiter", JUPITER_RADIUS, JUPITER_ORBIT_RADIUS, JUPITER_OMEGA, new Color(11, 230, 0));
        Planet saturn = new Planet("saturn", SATURN_RADIUS, SATURN_ORBIT_RADIUS, SATURN_OMEGA, new Color(247, 62, 201));

        this.planets.add(mercury);
        this.planets.add(venus);
        this.planets.add(earth);
        this.planets.add(mars);
        this.planets.add(jupiter);
        this.planets.add(saturn);
        moonlit.showWindow();
    }

    public static int myMap(double x) {
        return (int) Moonlit.map(x, 0.0, SUN_RADIUS, 0.0, 15.0);
    }

    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, 255, 0, 0);
        moonlit.fillCircle(g, 450, 350, myMap(SUN_RADIUS));

        this.planets.forEach(p -> {
            p.draw(g);
        });

        moonlit.setColor(g, 255, 255, 0);
        moonlit.drawString(g, 20, 40, (int) (Math.floor(moonlit.elapsedTime * 20.0)) + "days passed");
    }

    private class Planet {
        private String name;
        private double radius, orbit_radius, omega, theta = 0.0;
        private Color color;

        public Planet(String name, double radius, double orbit_radius, double omega, Color color) {
            this.name = name;
            this.radius = Moonlit.map(radius, 0.0, Main.SATURN_RADIUS, 0.0, 500000.0);
            this.orbit_radius = Moonlit.map(orbit_radius, 0.0, Main.SATURN_ORBIT_RADIUS, 0.0, 22000000.0);
            this.omega = omega;
            this.color = color;
        }

        public void draw(Graphics g) {
            Moonlit moonlit = Moonlit.getInstance();
            moonlit.setColor(g, 255, 255, 0);
            moonlit.setStroke(g, 2);
            moonlit.drawCircle(g, 450, 350, myMap(this.orbit_radius));
            moonlit.setColor(g, color);
            theta = -Math.toRadians(this.omega * moonlit.elapsedTime);
            moonlit.fillCircle(g, 450 + (int) (myMap(this.orbit_radius) * Math.cos(theta)),
                    350 + (int) (myMap(this.orbit_radius) * Math.sin(theta)), myMap(this.radius));
        }
    }
}