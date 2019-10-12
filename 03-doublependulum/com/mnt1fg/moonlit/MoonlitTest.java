/**
 * MoonlitTest.java
 * This is a class which I use this file to debug.
 * 
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

import java.awt.Graphics;

import com.mnt1fg.moonlit.Moonlit;
import com.mnt1fg.moonlit.MoonlitInterface;


public class MoonlitTest implements MoonlitInterface{

    public static void main(String[] args) {
        new MoonlitTest();
    }

    public MoonlitTest(){
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(600, 400);
        moonlit.setTicks(20);
        moonlit.register(this);
        moonlit.showWindow();

        //2019/09/09
        //Moonlit.log(Moonlit.map(100.0, 200.0, 400.0, 0.0, 20.0));

    }

    private int t = 0;
    @Override
    public void onUpdate(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        t++;
    }
}