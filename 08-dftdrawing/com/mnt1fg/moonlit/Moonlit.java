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
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class Moonlit extends JFrame implements KeyListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private static Moonlit instance = null;
    private MoonlitPanel panel = null;
    // how many times onUpdate() is called.
    public double ticks = 10;
    // background color
    private Color backgroundColor = Color.white;
    private int width, height;
    private boolean setupOk = false;
    public boolean isFirst = true;
    public boolean noLoop = false;
    private int offsetWidth = 0, offsetHeight = 0;

    BufferedImage _img;
    Graphics2D _g2d;

    public double elapsedTime = 0.0;
    private int playSpeed = 1;

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
        _img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        _g2d = (Graphics2D) _img.getGraphics();
        setupOk = true;
    }

    public void setTitle(String name) {
        this.setName(name);
    }

    public void showWindow() {
        addKeyListener(this);
        if (!setupOk) {
            Moonlit.log("you must call createWindow method first.");
            System.exit(0);
        }
        this.setVisible(true);
    }

    public void setTicks(double ticks) {
        if (ticks <= 0) {
            Moonlit.log("ticks must be bigger than 0");
            System.exit(0);
        }
        this.ticks = ticks;
    }

    public void setPlaySpeed(int n) {
        this.playSpeed = n;
    }

    public int getPlaySpeed() {
        return this.playSpeed;
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
        g.drawRect(x + this.offsetWidth, y + this.offsetHeight, width, height);
    }

    public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
        g.drawLine(x1 + this.offsetWidth, y1 + this.offsetHeight, x2 + this.offsetWidth, y2 + this.offsetHeight);
    }

    public void drawArc(Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle) {
        g.drawArc(x + this.offsetWidth, y + this.offsetHeight, width, height, startAngle, arcAngle);
    }

    public void drawCircle(Graphics g, int x, int y, int radius) {
        g.drawArc(x - radius + this.offsetWidth, y - radius + this.offsetHeight, radius * 2, radius * 2, 0, 360);
    }

    public void fillRect(Graphics g, int x, int y, int width, int height) {
        g.fillRect(x + this.offsetWidth, y + this.offsetHeight, width, height);
    }

    public void fillArc(Graphics g, int x, int y, int width, int height, int startAngle, int arcAngle) {
        g.fillArc(x + this.offsetWidth, y + this.offsetHeight, width, height, startAngle, arcAngle);
    }

    public void fillCircle(Graphics g, int x, int y, int radius) {
        g.fillArc(x - radius + this.offsetWidth, y - radius + this.offsetHeight, radius * 2, radius * 2, 0, 360);
    }

    public void drawString(Graphics g, int x, int y, String str) {
        g.drawString(str, x + this.offsetWidth, y + this.offsetHeight);
    }

    /**
     * for double value
     */
    public void drawRect(Graphics g, double x, double y, double width, double height) {
        this.drawRect(g, (int) x, (int) y, (int) width, (int) height);
    }

    public void drawLine(Graphics g, double x1, double y1, double x2, double y2) {
        this.drawLine(g, (int) x1, (int) y1, (int) x2, (int) y2);
    }

    public void drawArc(Graphics g, double x, double y, double width, double height, double startAngle,
            double arcAngle) {
        this.drawArc(g, (int) x, (int) y, (int) width, (int) height, (int) startAngle, (int) arcAngle);
    }

    public void drawCircle(Graphics g, double x, double y, double radius) {
        this.drawCircle(g, (int) x, (int) y, (int) radius);
    }

    public void fillRect(Graphics g, double x, double y, double width, double height) {
        this.fillRect(g, (int) x, (int) y, (int) width, (int) height);
    }

    public void fillArc(Graphics g, double x, double y, double width, double height, double startAngle,
            double arcAngle) {
        this.fillArc(g, (int) x, (int) y, (int) width, (int) height, (int) startAngle, (int) arcAngle);
    }

    public void fillCircle(Graphics g, double x, double y, double radius) {
        this.fillCircle(g, (int) x, (int) y, (int) radius);
    }

    public void drawString(Graphics g, double x, double y, String str) {
        this.drawString(g, (int) x, (int) y, str);
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        _g2d.setColor(backgroundColor);
        _g2d.fillRect(0, 0, this.width, this.height);
    }

    public void translate(int offsetWidth, int offsetHeight) {
        this.offsetWidth = offsetWidth;
        this.offsetHeight = offsetHeight;
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

    public static double[] createRandomArray(int length, double min, double max) {
        double[] ary = new double[length];
        double range = max - min;
        for (int i = 0; i < length; i++) {
            ary[i] = Math.random() * range + min;
        }
        return ary;
    }

    public void repaint() {
        this.panel.repaint();
    }

    /**
     * for compiling
     */

    public static ComplexNumber cn(double re, double im) {
        return new ComplexNumber(re, im);
    }

    private class MoonlitPanel extends JPanel implements MouseInputListener {

        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private ArrayList<MoonlitInterface> updateClasses = new ArrayList<MoonlitInterface>();

        public MoonlitPanel() {
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void register(MoonlitInterface cls) {
            this.updateClasses.add(cls);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            // g.setColor(Moonlit.getInstance().getBackgroundColor());
            // g.fillRect(0, 0, Moonlit.getInstance().getWidth(),
            // Moonlit.getInstance().getHeight());
            if (Moonlit.getInstance().getAntiAliasing()) {
                // 図形や線のアンチエイリアシングの有効化
                _g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // 文字描画のアンチエイリアシングの有効化
                _g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
            this.updateClasses.forEach(c -> c.onUpdate(_g2d));
            g.drawImage(_img, 0, 0, null);
            final double _ticks = Moonlit.getInstance().ticks;
            final MoonlitPanel _panel = this;
            if (Moonlit.getInstance().isFirst && !noLoop) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                _panel.repaint();
                                TimeUnit.NANOSECONDS
                                        .sleep((long) (100000000 / _ticks / Moonlit.getInstance().getPlaySpeed()));
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

        ArrayList<Consumer<MouseEvent>> mouseDraggedArray = new ArrayList<>();

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseDraggedArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mouseMovedArray = new ArrayList<>();

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseMovedArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mouseClickedArray = new ArrayList<>();

        @Override
        public void mouseClicked(MouseEvent e) {
            mouseClickedArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mousePressedArray = new ArrayList<>();

        @Override
        public void mousePressed(MouseEvent e) {
            mousePressedArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mouseReleasedArray = new ArrayList<>();

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseReleasedArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mouseEnteredArray = new ArrayList<>();

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseEnteredArray.forEach(a -> a.accept(e));
        }

        ArrayList<Consumer<MouseEvent>> mouseExitedArray = new ArrayList<>();

        @Override
        public void mouseExited(MouseEvent e) {
            mouseExitedArray.forEach(a -> a.accept(e));
        }

    }

    ArrayList<Consumer<KeyEvent>> keyTypedArray = new ArrayList<>();

    public void onKeyTyped(Consumer<KeyEvent> f) {
        keyTypedArray.add(f);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        keyTypedArray.forEach(a -> a.accept(e));
    }

    ArrayList<Consumer<KeyEvent>> keyPressedArray = new ArrayList<>();

    public void onKeyPressed(Consumer<KeyEvent> f) {
        keyPressedArray.add(f);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressedArray.forEach(a -> a.accept(e));
    }

    ArrayList<Consumer<KeyEvent>> keyReleasedArray = new ArrayList<>();

    public void onKeyReleased(Consumer<KeyEvent> f) {
        keyReleasedArray.add(f);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyReleasedArray.forEach(a -> a.accept(e));
    }

    public void onMouseDragged(Consumer<MouseEvent> f) {
        this.panel.mouseDraggedArray.add(f);
    }

    public void onMouseMoved(Consumer<MouseEvent> f) {
        this.panel.mouseMovedArray.add(f);
    }

    public void onMouseClicked(Consumer<MouseEvent> f) {
        this.panel.mouseClickedArray.add(f);
    }

    public void onMousePressed(Consumer<MouseEvent> f) {
        this.panel.mousePressedArray.add(f);
    }

    public void onMouseReleased(Consumer<MouseEvent> f) {
        this.panel.mouseReleasedArray.add(f);
    }

    public void onMouseEntered(Consumer<MouseEvent> f) {
        this.panel.mouseEnteredArray.add(f);
    }

    public void onMouseExited(Consumer<MouseEvent> f) {
        this.panel.mouseExitedArray.add(f);
    }

}