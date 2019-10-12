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

public class MVector {
    public double x, y, z;

    public MVector(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0.0;
    }

    public MVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MVector multiply(double a) {
        return new MVector(this.x * a, this.y * a, this.z * a);
    }

    public MVector add(MVector v) {
        return new MVector(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public MVector subtract(MVector v) {
        return this.add(v.multiply(-1.0));
    }

    public double abs(){
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
}