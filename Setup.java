/**
 * CodeIt Project
 * @author Manato1fg
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

import java.io.IOException;
import java.io.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Setup {

    public static final String PACKAGE_NAME = "it.code";
    public static final String TEMPLATE_FILE = "" + 
    "//Automatically Created by CodeIt Setup\n" +
    "package $1;\n\n" +
    "import com.mnt1fg.moonlit.*;\n" +
    "import java.awt.Graphics;\n" +
    "import java.awt.Color;\n" +
    "import java.awt.event.KeyEvent;\n\n" +
    "public class Main implements MoonlitInterface { \n\n"+
    "    public static void main(String[] args) { \n" +
    "        new Main();\n"+
    "    }\n\n"+
    "    public Main() {\n"+
    "    }\n\n"+
    "    @Override\n"+
    "    public void onUpdate(Graphics g) { \n"+
    "        //draw something \n" + 
    "    }\n" +
    "    @Override\n"+
    "    public void onKeyPressed(KeyEvent e) {\n    }\n" +
    "    @Override\n" + 
    "    public void onKeyReleased(KeyEvent e) {\n    }\n" +
    "    @Override\n" + 
    "    public void onKeyTyped(KeyEvent e) {\n    }\n" +
    "    @Override\n" + 
    "    public void onKeyPressed(KeyEvent e) {\n    }\n" + 
    "    @Override\n" + 
    "    public void onKeyReleased(KeyEvent e) {\n    }\n" + 
    "    @Override\n"+ 
    "    public void onKeyTyped(KeyEvent e) {\n    }\n" +
    "}";

    public static final String TEMPLATE_BUILD = "" + 
    "javac ./com/mnt1fg/moonlit/Moonlit.java\n"+
    "javac $1/Main.java\n" +
    "java $1/Main";

    public static final String TEMPLATE_UPDATE = "" +
    "git clone https://github.com/Manato1fg/Moonlit.git\n"+
    "rm -rf ./com\n"+
    "mv -f ./Moonlit/com .\n"+
    "rm -rf ./Moonlit";

    public static void main(String[] args) {
        if (args.length != 2) {
            log("Usage: java Setup [vol] [name]");
        } else {
            int vol = Integer.parseInt(args[0]);
            String name = args[1].toLowerCase().replace("-", "_");
            String folderName = vol+"-"+name;
            Runtime runtime = Runtime.getRuntime();
            try{
                runtime.exec("mkdir " + folderName).waitFor();
                runtime.exec("git clone https://github.com/Manato1fg/Moonlit.git").waitFor();
                runtime.exec("mv -f ./Moonlit/com "+folderName).waitFor();
                runtime.exec("rm -rf ./Moonlit").waitFor();
                runtime.exec("mkdir -p "+folderName + "/" + PACKAGE_NAME.replace(".", "/")+"/"+name).waitFor();
                File newFile = new File(folderName + "/" + PACKAGE_NAME.replace(".", "/")+"/"+name+"/"+"Main.java");
                // FileWriterクラスのオブジェクトを生成する
                FileWriter file1 = new FileWriter(newFile);
                // PrintWriterクラスのオブジェクトを生成する
                PrintWriter pw1 = new PrintWriter(new BufferedWriter(file1));
                pw1.print(Setup.TEMPLATE_FILE.replace("$1", PACKAGE_NAME+"."+name));
                // ファイルを閉じる
                pw1.close();

                // FileWriterクラスのオブジェクトを生成する
                FileWriter file2 = new FileWriter(folderName + "/build.sh");
                // PrintWriterクラスのオブジェクトを生成する
                PrintWriter pw2 = new PrintWriter(new BufferedWriter(file2));
                pw2.print(Setup.TEMPLATE_BUILD.replace("$1", PACKAGE_NAME.replace(".", "/") + "/" + name));
                // ファイルを閉じる
                pw2.close();

                runtime.exec("chmod 777 "+ folderName + "/build.sh").waitFor();

                // FileWriterクラスのオブジェクトを生成する
                FileWriter file3 = new FileWriter(folderName + "/update.sh");
                // PrintWriterクラスのオブジェクトを生成する
                PrintWriter pw3 = new PrintWriter(new BufferedWriter(file3));
                pw3.print(Setup.TEMPLATE_UPDATE);
                // ファイルを閉じる
                pw3.close();
                runtime.exec("chmod 777 " + folderName + "/update.sh").waitFor();

                log("Setup finish");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void log(String message) {
        System.out.println("[CodeIt] "+message);
    }
}