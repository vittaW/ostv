package com.vittaw.mvplibrary.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cdy on 2016/2/3.
 */
public class DimenTool {


    public static void gen() {
        File file = new File("./mvplibrary/src/main/res/values/dimens.xml");
        BufferedReader reader = null;
//        StringBuilder sw180 = new StringBuilder();
        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw380 = new StringBuilder();
        StringBuilder sw410 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw760 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder sw900 = new StringBuilder();
        StringBuilder sw1024 = new StringBuilder();
//        StringBuilder sw1280 = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;

            while ((tempString = reader.readLine()) != null) {

                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    float num = Float.valueOf(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));

                    /**
                     * 根据UI画布大小比例进行换算，UI图相对分辨率为1334x750
                     * 设备默认缩放因子密度为 2  = 320 / 160
                     */
//                    int density = 2;
//                    int width = 750 / density;

                    /**
                     * 如果设计图尺寸为1080x1920
                     * 高分率缩放因子密度一般为 3 = 480 / 160 ，则：
                     * 可选项，根据你实际的UI设计图来定义
                     */
                    int density = 3;
                    int width = 1080 / density;

//                    sw180.append(start).append((int) Math.round(num * 180 / width)).append(end).append("\n");
                    sw240.append(start).append(Math.round(num * 240 / width)).append(end).append("\n");
                    sw320.append(start).append(Math.round(num * 320 / width)).append(end).append("\n");
                    //360 小米，oppo
                    sw360.append(start).append(Math.round(num * 360 / width)).append(end).append("\n");

                    sw380.append(start).append(Math.round(num * 384 / width)).append(end).append("\n");
                    //411dp 华为
                    sw410.append(start).append(Math.round(num * 410 / width)).append(end).append("\n");

                    sw480.append(start).append(Math.round(num * 480 / width)).append(end).append("\n");

                    //平板尺寸
                    sw600.append(start).append(Math.round(num * 600 / width)).append(end).append("\n");
                    sw720.append(start).append(Math.round(num * 720 / width)).append(end).append("\n");

                    sw760.append(start).append(Math.round(num * 768 / width)).append(end).append("\n");
                    sw800.append(start).append(Math.round(num * 800 / width)).append(end).append("\n");
                    sw900.append(start).append(Math.round(num * 900 / width)).append(end).append("\n");

                    sw1024.append(start).append(Math.round(num * 1024 / width)).append(end).append("\n");
//                    sw1280.append(start).append((int) Math.round(num * 1280 / width)).append(end).append("\n");


                } else {
//                    sw180.append(tempString).append("\n");
                    sw240.append(tempString).append("\n");
                    sw320.append(tempString).append("\n");
                    sw360.append(tempString).append("\n");
                    sw380.append(tempString).append("\n");
                    sw410.append(tempString).append("\n");
                    sw480.append(tempString).append("\n");
                    sw600.append(tempString).append("\n");
                    sw720.append(tempString).append("\n");

                    sw760.append(tempString).append("\n");
                    sw800.append(tempString).append("\n");
                    sw900.append(tempString).append("\n");

                    sw1024.append(tempString).append("\n");
//                    sw1280.append(tempString).append("\n");
                }
                line++;
            }
            reader.close();
            System.out.println("<!--  sw480 -->");
            System.out.println(sw480);
            System.out.println("<!--  sw600 -->");
            System.out.println(sw600);

            System.out.println("<!--  sw720 -->");
            System.out.println(sw720);
            System.out.println("<!--  sw1024 -->");
            System.out.println(sw1024);

//            String sw180file = "./mvplibrary/src/main/res/values-sw180dp/dimens.xml";
            String sw240file = "./mvplibrary/src/main/res/values-sw240dp";
            String sw320file = "./mvplibrary/src/main/res/values-sw320dp";
            String sw360file = "./mvplibrary/src/main/res/values-sw360dp";
            String sw380file = "./mvplibrary/src/main/res/values-sw380dp";
            String sw410file = "./mvplibrary/src/main/res/values-sw410dp";
            String sw480file = "./mvplibrary/src/main/res/values-sw480dp";
            String sw600file = "./mvplibrary/src/main/res/values-sw600dp";
            String sw720file = "./mvplibrary/src/main/res/values-sw720dp";

            String sw760file = "./mvplibrary/src/main/res/values-sw760dp";
            String sw800file = "./mvplibrary/src/main/res/values-sw800dp";
            String sw900file = "./mvplibrary/src/main/res/values-sw900dp";

            String sw1024file = "./mvplibrary/src/main/res/values-sw1024dp";
//            String sw1280file = "./mvplibrary/src/main/res/values-sw1280dp/dimens.xml";

//            writeFile(sw180file, sw180.toString());
            writeFile(sw240file, sw240.toString());
            writeFile(sw320file, sw320.toString());
            writeFile(sw360file, sw360.toString());
            writeFile(sw380file, sw380.toString());
            writeFile(sw410file, sw410.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw720file, sw720.toString());

            writeFile(sw760file, sw760.toString());
            writeFile(sw800file, sw800.toString());
            writeFile(sw900file, sw900.toString());

            writeFile(sw1024file, sw1024.toString());
//            writeFile(sw1280file, sw1280.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void writeFile(String fileName, String text) {
        PrintWriter out = null;
        try {
            //TODO ① 如果运行不成功，第一步，创建文件夹
//            File file = new File(fileName);
//            boolean mkdir = file.mkdir();
            //TODO ② 第二步，创建文件
            File file1 = new File(fileName + File.separator, "dimens.xml");
            if (!file1.exists()) {
                boolean newFile = file1.createNewFile();
            }
            FileWriter out1 = new FileWriter(file1);
            out = new PrintWriter(new BufferedWriter(out1));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    private static void genBaseDimensSize() {
        System.out.println("<!--   ******************************Font******************************* -->");
        for (int i = 6; i <= 36; i++) {
            StringBuilder sb = new StringBuilder("<dimen name=\"font_size_");
            sb.append(i).append("\">").append(i).append("sp</dimen>");
            System.out.println(sb.toString());
        }
        System.out.println("<!--   ******************************Widget******************************* -->");
        for (int i = 1; i <= 400; i++) {
            StringBuilder sb = new StringBuilder("<dimen name=\"widget_size_");
            sb.append(i).append("\">").append(i).append("dp</dimen>");
            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) {
        gen();
//        genBaseDimensSize();
    }
}
