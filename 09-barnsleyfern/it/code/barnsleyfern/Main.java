//Automatically Created by CodeIt Setup
package it.code.barnsleyfern;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.awt.Color;
public class Main implements MoonlitInterface { 

    double x = 0;
    double y = 0;

    int width = 700;
    int height = 700;

    public static void main(String[] args) { 
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(width, height);
        moonlit.setBackgroundColor(Color.black);
        moonlit.register(this);
        moonlit.setTicks(5);
        moonlit.translate(width / 2, height);
        moonlit.showWindow();
    }



    @Override
    public void onUpdate(Graphics g) { 
        Moonlit moonlit = Moonlit.getInstance();

        for(int i = 0; i < 100; i++) {
            double r = Math.random();
            double xx, yy;
            if (r < 0.01) {
                xx = 0.0;
                yy = 0.16 * y;
            } else if (r < 0.86) {
                xx = 0.85 * x + 0.04 * y;
                yy = -0.04 * x + 0.85 * y + 1.6;
            } else if (r < 0.93) {
                xx = 0.2 * x - 0.26 * y;
                yy = 0.23 * x + 0.22 * y + 1.6;
            } else {
                xx = -0.15 * x + 0.28 * y;
                yy = 0.26 * x + 0.24 * y + 0.44;
            }
            x = xx;
            y = yy;
            // −2.1820 < x < 2.6558
            xx = Moonlit.map(xx, -2.1820, 2.6558, -width / 2, width / 2);
            // 0 ≤ y < 9.9983
            yy = Moonlit.map(yy, 0.0, 9.9983, (double) 0.0, -height);
            //Moonlit.log(Moonlit.map(xx, -width / 2, width / 2, 0.0, 1.0));
            Color color = Color.getHSBColor((float) Moonlit.map(xx, -width / 2, width / 2, 0.0, 1.0), 1.0f, 1.0f);
            moonlit.setColor(g, color);
            moonlit.fillCircle(g, xx, yy, 1);
        }
    }
}