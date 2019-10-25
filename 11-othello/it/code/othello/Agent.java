package it.code.othello;

import java.util.ArrayList;

import com.mnt1fg.moonlit.Moonlit;

import it.code.othello.Main.Point;
import it.code.othello.Main.Stage;

public class Agent {
    int color;
    public Agent(int color) {
        this.color = color;
    }

    public Point think(Stage stage) {
        int oppositeColor = -color;
        Point p = null;
        int minLoss1 = Integer.MAX_VALUE;
        int minLoss2 = Integer.MAX_VALUE;
        Stage minStage1 = null;
        ArrayList<Point> availablePoints1 = stage.getAvailablePoints(color);

        for(Point point1 : availablePoints1) {
            Stage stage1 = stage.put(point1.x, point1.y, color);
            ArrayList<Point> availablePoints2 = stage1.getAvailablePoints(oppositeColor);
            for(Point point2 : availablePoints2) {
                Stage stage2 = stage1.put(point2.x, point2.y, oppositeColor);
                int loss1 = stage2.evaluate(color);
                if(loss1 < minLoss1) {
                    minLoss1 = loss1;
                    minStage1 = stage2;
                }
            }

            if(minStage1 == null) {
                return point1;
            }

            ArrayList<Point> availablePoints3 = minStage1.getAvailablePoints(color);
            for(Point point3 : availablePoints3) {
                Stage stage3 = minStage1.put(point3.x, point3.y, color);
                int loss2 = stage3.evaluate(oppositeColor);
                if(minLoss2 > loss2) {
                    minLoss2 = loss2;
                    p = point1;
                }
            }
        }
        return p;
    }
}