//Automatically Created by CodeIt Setup
package it.code.paint;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Function;
import java.awt.Color;

public class Main implements MoonlitInterface { 

    Position lastPos = null;
    Position newPos = null;
    public static void main(String[] args) { 
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(600, 600);
        moonlit.setBackgroundColor(Color.white);
        moonlit.noLoop = true;
        moonlit.register(this);
        moonlit.onMouseDragged(onMouseDragged);
        moonlit.onMouseMoved(onMouseMoved);
        moonlit.showWindow();
    }

    @Override
    public void onUpdate(Graphics g) { 
        if(lastPos == null || newPos == null) return;
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.black);
        moonlit.setStroke(g, 10);
        moonlit.drawLine(g, lastPos.x, lastPos.y, newPos.x, newPos.y);
        lastPos = newPos;
        System.out.println("new Point("+l((double)lastPos.x / 6.0)+","+ l((double) lastPos.y / 6.0)+"),");
    }

    public static String l(double x) {
        return String.format("%.1f",x);
    }
    
    Consumer<MouseEvent> onMouseDragged = (MouseEvent e) -> {
        if(lastPos == null) {
            lastPos = new Position(e.getX(), e.getY());
        } else {
            if(lastPos.x != e.getX() || lastPos.y != e.getY()) {
                newPos = new Position(e.getX(), e.getY());
                Moonlit.getInstance().repaint();
            }
        }
    };

    Consumer<MouseEvent> onMouseMoved = (MouseEvent e) -> {
        lastPos = new Position(e.getX(), e.getY());
    };

    class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}