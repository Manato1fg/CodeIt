package com.mnt1fg.moonlit;

public class ComplexNumber {

    public double re = 0.0;
    public double im = 0.0;
    public double amp = 0.0;
    public double angle = 0.0;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
        this.update();
    }

    public void add(ComplexNumber a) {
        this.re += a.re;
        this.im += a.im;
        this.update();
    }

    public void subtract(ComplexNumber a) {
        this.re -= a.re;
        this.im -= a.im;
        this.update();
    }
    
    public void multiply(ComplexNumber a) {
        this.re = this.re * this.re - a.im * a.im;;
        this.im = this.re * a.im + a.re * this.im;
        this.update();
    }

    public void multiply(double a) {
        this.re *= a;
        this.im *= a;
        this.update();
    }

    public void update() {
        this.angle = Math.atan2(this.im, this.re);
        this.amp = Math.sqrt(this.re * this.re + this.im * this.im);
    }

    public static ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        ComplexNumber c = new ComplexNumber(0.0, 0.0);
        c.re = a.re + b.re;
        c.im = a.im + b.im;
        return c;
    }

    public static ComplexNumber subtract(ComplexNumber a, ComplexNumber b) {
        ComplexNumber c = new ComplexNumber(0.0, 0.0);
        c.re = a.re - b.re;
        c.im = a.im - b.im;
        return c;
    }

    public static ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        ComplexNumber c = new ComplexNumber(0.0, 0.0);
        c.re = a.re * a.re - b.im * b.im;
        c.im = a.re * b.im + b.re * a.im;
        return c;
    }

    public static ComplexNumber multiply(ComplexNumber a, double scale) {
        ComplexNumber c = new ComplexNumber(0.0, 0.0);
        c.re = a.re * scale;
        c.im = a.im * scale;
        return c;
    }

    public double getAngle() {
        return this.angle;
    }

    public double getAmp() {
        return this.amp;
    }

}