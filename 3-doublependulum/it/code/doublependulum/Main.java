//Automatically Created by CodeIt Setup
package it.code.doublependulum;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;

public class Main implements MoonlitInterface { 

    public static final double g = 9.80665;
    public static final double l1 = 10.0;
    public static final double l2 = 4.0;
    public static final double m1 = 2.0;
    public static final double m2 = 5.0;

    public static void main(String[] args) { 
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(800, 600);
    }

    @Override
    public void onUpdate(Graphics g) { 
    }
}