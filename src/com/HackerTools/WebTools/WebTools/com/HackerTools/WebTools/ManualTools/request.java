package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.ManualTools;

import com.HackerTools.WebTools.WebTools.WebTools_ManualTools;

import javax.swing.*;
import java.awt.*;

public class request implements WebTools_ManualTools {
    public static int type = 0;
    public static String name = "网站数据发送工具";

    public String[] init_main(){
        String[] info = new String[2];
        info[0] = String.valueOf(type);
        info[1] = name;
        return info;
    }

    public void start_main(com.HackerTools.WebTools.WebToolsAPI API){
        JFrame main_frame = new JFrame("网站数据发送工具");

        main_frame.setLayout(null); //使用绝对布局器
        main_frame.setSize(400,400);
        main_frame.setBackground(Color.WHITE);
        main_frame.setResizable(false); //禁止最大化或拉伸窗体

        main_frame.setVisible(true);
    }
}
