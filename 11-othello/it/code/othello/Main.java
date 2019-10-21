//Automatically Created by CodeIt Setup
package it.code.othello;

import com.mnt1fg.moonlit.*;
import java.awt.Graphics;
import java.awt.Color;
public class Main implements MoonlitInterface { 

    public static final int w = 60;
    public static final int line_width = 1;

    public static final int[][] stage = new int[8][8];

    public static final int BLACK = -1;
    public static final int SPACE = 0;
    public static final int WHITE = 1;

    public boolean isBlack = true;

    public static void main(String[] args) { 
        new Main();
    }

    public Main() {
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.createWindow(w * 8, w * 8);
        initStage();
        moonlit.noLoop = true;
        moonlit.register(this);
        moonlit.showWindow();
        moonlit.onMouseClicked((e) -> {
            int x = e.getX();
            int y = e.getY();
            int xx = (int) Math.floor(x / w);
            int yy = (int) Math.floor(y / w);
            put(xx, yy);
        });
    }

    public void initStage() {
        for(int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                stage[x][y] = SPACE;
            }
        }

        stage[3][3] = WHITE;
        stage[3][4] = BLACK;
        stage[4][3] = BLACK;
        stage[4][4] = WHITE;
    }
    

    public void put(int x, int y) {
        int color = isBlack ? BLACK : WHITE;
        int[] flags = new int[8];
        if(canPut(x, y, color, flags)) {
            stage[x][y] = color;
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
                    while(stage[x + j * x_flag][y + j * y_flag] != color) {
                        stage[x + j * x_flag][y + j * y_flag] = color;
                        j++;
                    }
                }
            }
            isBlack = !isBlack;
            Moonlit.getInstance().repaint();
        }
    }
    
    public static final int FLAG_INIT = -1;
    public static final int FLAG_PROGRESS = 0;
    public static final int FLAG_FOUND_SAME_COLOR = 1;
    public static final int FLAG_FOUND_SPACE = 2;

    public boolean canPut(int x, int y, int color, int[] flags) {
        if (stage[x][y] != SPACE) {
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

        //debugArray(flags);

        for(int i = 0; i < 8; i++) {
            if(flags[i] == FLAG_FOUND_SAME_COLOR) return true;
        }
        return false;
    }

    public void finish() {
        int[] count = new int[2];
        count[0] = 0;
        count[1] = 0;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (stage[x][y] == BLACK)
                    count[0]++;
                if (stage[x][y] == WHITE)
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

    public void setFlag(int i, int xx, int yy, int color, int[] flags) {
        if (xx >= 0 && yy >= 0 && xx < 8 && yy < 8) {
            if (flags[i] == FLAG_INIT) {
                if (stage[xx][yy] == color) {
                    flags[i] = FLAG_FOUND_SPACE;
                } else if (stage[xx][yy] == SPACE) {
                    flags[i] = FLAG_FOUND_SPACE;
                } else {
                    flags[i] = FLAG_PROGRESS;
                }
            } else if (flags[i] == FLAG_PROGRESS) {
                if (stage[xx][yy] == color) {
                    flags[i] = FLAG_FOUND_SAME_COLOR;
                } else if (stage[xx][yy] == SPACE) {
                    flags[i] = FLAG_FOUND_SPACE;
                } else {
                    flags[i] = FLAG_PROGRESS;
                }
            }
        }
    }

    public boolean setRedMarker(Graphics g) {
        Moonlit moonlit = Moonlit.getInstance();
        int color = isBlack ? BLACK : WHITE;
        boolean none = true;
        for (int p = 0; p < 8; p++) {
            for (int q = 0; q < 8; q++) {
                int[] flags = new int[8];
                if (canPut(p, q, color, flags)) {
                    none = false;
                    moonlit.setColor(g, Color.red);
                    int pp = p * w + line_width;
                    int qq = q * w + line_width;
                    moonlit.fillCircle(g, pp + w / 2, qq + w / 2, w / 2 - 10);
                }
            }
        }
        return none;
    }

    public void switchDueToNoPlace() {
        String s_color = isBlack ? "黒" : "白";
        Moonlit.log("%sはもう置く場所がありません。", s_color);
        isBlack = !isBlack;
    }

    @Override
    public void onUpdate(Graphics g) { 
        Moonlit moonlit = Moonlit.getInstance();
        moonlit.setColor(g, Color.black);
        moonlit.fillRect(g, 0, 0, w * 8, w * 8);
        for(int i = 0; i < 8; i++) {
            int x = i * w + line_width;
            for (int j = 0; j < 8; j++) {
                int y = j * w + line_width;
                moonlit.setColor(g, Color.green);
                moonlit.fillRect(g, x, y, w - 2 * line_width, w - 2 * line_width);
                if(stage[i][j] != SPACE) {
                    Color color = stage[i][j] == BLACK ? Color.black : Color.white;
                    moonlit.setColor(g, color);
                    moonlit.fillCircle(g, x + w / 2, y + w / 2, w / 2 - 5);
                }
            }
        }

        
        if(setRedMarker(g) == true) {
            switchDueToNoPlace();
            if(setRedMarker(g) == true) {
                switchDueToNoPlace();
                Moonlit.log("ゲーム終了");
                finish();
                return;
            }
        }
    }

    public static void debugArray(int[] objects) {
        String str = "[";
        for (int i = 0; i < objects.length - 1; i++) {
            str += Integer.toString(objects[i]) + ",";
        }
        str += Integer.toString(objects[objects.length - 1]) + "]";
        Moonlit.log(str);
    }
}