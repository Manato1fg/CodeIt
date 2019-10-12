/**
 * Moonlit Project
 * @author Manato1fg
 * 
 * This is a library written in Java which enables you to make canvas easily.
 * This project is inspired by The Coding Train.
 * 
 * MIT License
 * 
 * Copyright (c) 2019 Manato

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mnt1fg.moonlit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Moonlit extends JFrame {
    private static Moonlit instance = null;
    private MoonlitPanel panel = null;
    // how many times onUpdate() is called.
    public int ticks = 10;
    // background color
    private Color backgroundColor = Color.white;
    private int width, height;
    private boolean setupOk = false;
    public boolean isFirst = true;

    public double elapsedTime = 0.0;

    private boolean antiAliasing = true;

    public static Moonlit getInstance() {

        if (instance == null) {
            instance = new Moonlit();
        }

        return instance;
    }

    /**
     * register class to let its onUpdate() be called.
     * 
     * @param cls must implements MoonlitInterface
     */
    public void register(MoonlitInterface cls) {
        this.panel.register(cls);
    }

    public void createWindow(int width, int height) {
        this.setSize(width, height);
        this.width = width;
        this.height = height;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new MoonlitPanel();
        this.add(this.panel);
        setupOk = true;
    }

    public void showWindow() {
        if (!setupOk) {
            Moonlit.log("you must call createWindow method first.");
            System.exit(0);
        }
        this.setVisible(true);
    }

    public void setTicks(int ticks) {
        if (ticks <= 0) {
            Moonlit.log("ticks must be bigger than 0");
            System.exit(0);
        }
        this.ticks = ticks;
    }

    /**
     * draw functions
     */

    public void setColor(Graphics _g, int r, int g, int b) {
        _g.setColor(new Color(r, g, b));
    }

    public void setColor(Graphics _g, Color color) {
        _g.setColor(color);
    }

    public void setStroke(Graphics g, int strokeSize) {
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(strokeSize);
        g2d.setStroke(bs);
    }

    public void drawRect(Graphics g, int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawArc(Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle) {
        g.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    public void drawCircle(Graphics g, int x, int y, int radius) {
        g.drawArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
    }

    public void fillRect(Graphics g, int x, int y, int width, int height) {
        g.fillRect(x, y, width, height);
    }

    public void fillArc(Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle) {
        g.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    public void fillCircle(Graphics g, int x, int y, int radius) {
        g.fillArc(x - radius, y - radius, radius * 2, radius * 2, 0, 360);
    }

    public void drawString(Graphics g, int x, int y, String str) {
        g.drawString(str, x, y);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setAntiAliasing(boolean flag) {
        this.antiAliasing = flag;
    }

    public boolean getAntiAliasing() {
        return this.antiAliasing;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    /**
     * utilities
     */

    public static void log(Object obj) {
        System.out.println("[Moonlit] " + obj.toString());
    }

    public static double map(double x, double originalMin, double originalMax, double destMin, double destMax) {
        if (originalMax <= originalMin || destMax <= destMin) {
            Moonlit.log("min must be smaller than max");
            return x;
        }

        x -= originalMin;
        originalMax -= originalMin;
        double ratio = x / originalMax;
        destMax -= destMin;
        double y = destMax * ratio;
        return y + destMin;
    }

    private class MoonlitPanel extends JPanel {

        private ArrayList<MoonlitInterface> updateClasses = new ArrayList<MoonlitInterface>();

        public void register(MoonlitInterface cls) {
            this.updateClasses.add(cls);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Moonlit.getInstance().getBackgroundColor());
            g.fillRect(0, 0, Moonlit.getInstance().getWidth(), Moonlit.getInstance().getHeight());
            if (Moonlit.getInstance().getAntiAliasing()) {
                Graphics2D g2 = (Graphics2D) g;
                // 図形や線のアンチエイリアシングの有効化
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 文字描画のアンチエイリアシングの有効化
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
            this.updateClasses.forEach(c -> c.onUpdate(g));
            final int _ticks = Moonlit.getInstance().ticks;
            final MoonlitPanel _panel = this;
            if (Moonlit.getInstance().isFirst) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                _panel.repaint();
                                Thread.sleep((int) (1000 / _ticks));
                                Moonlit.getInstance().elapsedTime += 1.0 / (double) _ticks;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                Moonlit.getInstance().isFirst = false;
            }
        }

    }
}
