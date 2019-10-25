//Automatically Created by CodeIt Setup
package it.code.othello;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Main implements MoonlitInterface {

    public static final int w = 60;
    public static final int line_width = 1;

    public static final int BLACK = -1;
    public static final int SPACE = 0;
    public static final int WHITE = 1;

    public boolean isBlack = true;
    public boolean isPlayerTurn = false;

    public Stage stage;

    public Agent agent;

    public boolean isThinking = false;
    public boolean hasUpdated = false;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(w * 8, w * 8);
        initStage();
        this.coin();
        agent = new Agent(!isPlayerTurn ? BLACK : WHITE);
        moonlit.register(this);
        moonlit.showWindow();
        moonlit.onMouseClicked((e) -> {
            if (isPlayerTurn) {
                int x = e.getX();
                int y = e.getY();
                int xx = (int) Math.floor(x / w);
                int yy = (int) Math.floor(y / w);
                put(xx, yy);
            }
        });
    }

    public void initStage() {
        this.stage = new Stage();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                stage.set(x, y, SPACE);
            }
        }

        stage.set(3, 3, WHITE);
        stage.set(3, 4, BLACK);
        stage.set(4, 3, BLACK);
        stage.set(4, 4, WHITE);
    }

    public void coin() {
        isPlayerTurn = Math.random() < 0.5;
        String player_color = isPlayerTurn ? "黒" : "白";
        Moonlit.log("あなたの駒の色は%sです。", player_color);
        log();
    }

    public void log() {
        String name = isPlayerTurn ? "あなた" : "コンピュータ";
        Moonlit.log("%sの手番です。", name);
    }

    public void put(int x, int y) {
        int color = isBlack ? BLACK : WHITE;
        Stage newStage = this.stage.put(x, y, color);
        if (this.stage.isAvailable(x, y, color)) {
            this.stage = newStage;
            isBlack = !isBlack;
            isPlayerTurn = !isPlayerTurn;
            log();
            this.hasUpdated = true;
        }
    }

    public void finish() {
        int[] count = new int[2];
        count[0] = 0;
        count[1] = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (stage.get(x, y) == BLACK)
                    count[0]++;
                if (stage.get(x, y) == WHITE)
                    count[1]++;
            }
        }
        String state = "";
        if (count[0] == count[1])
            state = "引き分け";
        if (count[0] > count[1])
            state = "黒の勝ち";
        if (count[0] < count[1])
            state = "白の勝ち";

        Moonlit.log("黒:白 %d:%d で%sです。", count[0], count[1], state);
    }

    public void setRedMarker(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        int color = isBlack ? BLACK : WHITE;
        for (Point p : this.stage.getAvailablePoints(color)) {
            int[] flags = new int[8];
            if (stage.canPut(p.x, p.y, color, flags)) {
                moonlit.setColor(g, Color.red);
                int pp = p.x * w + line_width;
                int qq = p.y * w + line_width;
                moonlit.fillCircle(g, pp + w / 2, qq + w / 2, w / 2 - 10);
            }
        }
    }

    public void switchDueToNoPlace() {
        String s_color = isBlack ? "黒" : "白";
        Moonlit.log("%sはもう置く場所がありません。", s_color);
        isBlack = !isBlack;
        isPlayerTurn = !isPlayerTurn;
    }

    public void pause() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdate(Graphics g) { 
        if(isThinking) {
            this.pause();
            isThinking = false;
            Point p = agent.think(this.stage);
            this.put(p.x, p.y);
            return;
        }
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.black);
        moonlit.fillRect(g, 0, 0, w * 8, w * 8);
        for(int i = 0; i < 8; i++) {
            int x = i * w + line_width;
            for (int j = 0; j < 8; j++) {
                int y = j * w + line_width;
                moonlit.setColor(g, Color.green);
                moonlit.fillRect(g, x, y, w - 2 * line_width, w - 2 * line_width);
                if(stage.get(i, j) != SPACE) {
                    Color color = stage.get(i, j) == BLACK ? Color.black : Color.white;
                    moonlit.setColor(g, color);
                    moonlit.fillCircle(g, x + w / 2, y + w / 2, w / 2 - 5);
                }
            }
        }
        if(!hasUpdated) return;
        hasUpdated = false;
        int color = isBlack ? BLACK : WHITE;
        if(this.stage.getAvailablePoints(color).size() == 0) {
            switchDueToNoPlace();
            color = isBlack ? BLACK : WHITE;
            if(this.stage.getAvailablePoints(color).size() == 0) {
                switchDueToNoPlace();
                Moonlit.log("ゲーム終了");
                finish();
                return;
            }
        }


        if(!isPlayerTurn) {
            isThinking = true;
        }
        
    }

    static class Stage {
        public int[][] stage = new int[8][8];

        public int get(int x, int y) {
            return this.stage[x][y];
        }

        public Stage put(int x, int y, int color) {
            Stage newStage = new Stage();
            //deep copy
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    newStage.set(i, j, this.stage[i][j]);
                }
            }
            int[] flags = new int[8];
            if(canPut(x, y, color, flags)) {
                newStage.set(x, y, color);
                for(int i = 0; i < 8; i++) {
                    if(flags[i] == FLAG_FOUND_SAME_COLOR) {
                        int x_flag = 0;
                        if (i == 0 || i == 6 || i == 7)
                            x_flag = -1;
                        if (i == 2 || i == 3 || i == 4)
                            x_flag = 1;
                        int y_flag = 0;
                        if (i == 0 || i == 1 || i == 2)
                            y_flag = -1;
                        if (i == 4 || i == 5 || i == 6)
                            y_flag = 1;
                        int j = 1;
                        while(newStage.get(x + j * x_flag, y + j * y_flag) != color) {
                            newStage.set(x + j * x_flag, y + j * y_flag, color);
                            j++;
                        }
                    }
                }
            } else {
                return this;
            }
            return newStage;
        }

        public int calcIfPut(Point p, int color) {
            return this.calcIfPut(p.x, p.y, color);
        }

        public int calcIfPut(int x, int y, int color) {
            int[] flags = new int[8];
            int count = 0;
            if(canPut(x, y, color, flags)) {
                count ++;
                for(int i = 0; i < 8; i++) {
                    if(flags[i] == FLAG_FOUND_SAME_COLOR) {
                        int x_flag = 0;
                        if (i == 0 || i == 6 || i == 7)
                            x_flag = -1;
                        if (i == 2 || i == 3 || i == 4)
                            x_flag = 1;
                        int y_flag = 0;
                        if (i == 0 || i == 1 || i == 2)
                            y_flag = -1;
                        if (i == 4 || i == 5 || i == 6)
                            y_flag = 1;
                        int j = 1;
                        while(this.get(x + j * x_flag, y + j * y_flag) != color) {
                            count ++;
                            j++;
                        }
                    }
                }
            }
            return count;
        }

        public void set(int x, int y, int color) {
            this.stage[x][y] = color;
        }

        //損失関数
        public int evaluate(int color) {
            int loss = 0;
            int max = 0;
            int oppositeColor = -color;
            ArrayList<Point> availablePoints = this.getAvailablePoints(oppositeColor);
            for(Point p : availablePoints) {
                // corner
                if((p.x == 0 || p.x == 7) && (p.y == 0 || p.y == 7)) {
                    loss += 5;
                }

                int count = calcIfPut(p, oppositeColor);
                if(count > max) {
                    max = count;
                }
            }
            loss += max;
            return loss;
        }

        public boolean isAvailable(int x, int y, int color) {
            int[] flags = new int[8];
            return this.canPut(x, y, color, flags);
        }

        public ArrayList<Point> getAvailablePoints(int color) {
            ArrayList<Point> points = new ArrayList<>();
            for (int p = 0; p < 8; p++) {
                for (int q = 0; q < 8; q++) {
                    if (this.isAvailable(p, q, color)) {
                        points.add(new Point(p, q));
                    }
                }
            }
            return points;
        }

        public static final int FLAG_INIT = -1;
        public static final int FLAG_PROGRESS = 0;
        public static final int FLAG_FOUND_SAME_COLOR = 1;
        public static final int FLAG_FOUND_SPACE = 2;

        public boolean canPut(int x, int y, int color, int[] flags) {
            if (this.get(x, y) != SPACE) {
                //Moonlit.log("(%d, %d)にはすでに駒が置かれています", x, y);
                return false;
            }

            for (int i = 0; i < 8; i++) {
                flags[i] = FLAG_INIT;
            }

            for (int i = 1; i < 8; i++) {
                //左上
                setFlag(0, x - i, y - i, color, flags);
                //上
                setFlag(1, x    , y - i, color, flags);
                //右上
                setFlag(2, x + i, y - i, color, flags);
                //右
                setFlag(3, x + i, y    , color, flags);
                //右下
                setFlag(4, x + i, y + i, color, flags);
                //下
                setFlag(5, x    , y + i, color, flags);
                //左下
                setFlag(6, x - i, y + i, color, flags);
                //左
                setFlag(7, x - i, y    , color, flags);
            }

            for(int i = 0; i < 8; i++) {
                if(flags[i] == FLAG_FOUND_SAME_COLOR) return true;
            }
            return false;
        }

        public void setFlag(int i, int xx, int yy, int color, int[] flags) {
            if (xx >= 0 && yy >= 0 && xx < 8 && yy < 8) {
                if (flags[i] == FLAG_INIT) {
                    if (this.get(xx, yy) == color) {
                        flags[i] = FLAG_FOUND_SPACE;
                    } else if (this.get(xx, yy) == SPACE) {
                        flags[i] = FLAG_FOUND_SPACE;
                    } else {
                        flags[i] = FLAG_PROGRESS;
                    }
                } else if (flags[i] == FLAG_PROGRESS) {
                    if (this.get(xx, yy) == color) {
                        flags[i] = FLAG_FOUND_SAME_COLOR;
                    } else if (this.get(xx, yy) == SPACE) {
                        flags[i] = FLAG_FOUND_SPACE;
                    } else {
                        flags[i] = FLAG_PROGRESS;
                    }
                }
            }
        }
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}