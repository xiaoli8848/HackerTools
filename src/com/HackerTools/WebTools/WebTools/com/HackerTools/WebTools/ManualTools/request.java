package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.ManualTools;

import com.HackerTools.WebTools.WebTools.WebTools_ManualTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class request implements WebTools_ManualTools {
    public static int type = 0;
    public static String name = "网站数据发送工具";
    private static final Font font_TitleLabel = new Font("宋体", Font.PLAIN, 25);  //标题字体
    private static final Font font_Label = new Font("宋体", Font.PLAIN, 15);  //标签字体
    private static final Font font_TextField = new Font("宋体", Font.PLAIN, 20); //文本框字体
    private static String Type;

    public String[] init_main() {
        String[] info = new String[2];
        info[0] = String.valueOf(type);
        info[1] = name;
        return info;
    }

    public void start_main(com.HackerTools.WebTools.WebToolsAPI API) {
        JFrame main_frame = new JFrame("网站数据发送工具");

        main_frame.setLayout(null); //使用绝对布局器
        main_frame.setSize(400, 400);
        main_frame.setBackground(Color.WHITE);
        main_frame.setResizable(false); //禁止最大化或拉伸窗体

        //要发送给数据的URL标签
        JLabel urlLabel = new JLabel("URL：");
        urlLabel.setBounds(50, 50, 70, 30);
        urlLabel.setFont(font_Label);
        main_frame.add(urlLabel);

        //要发送给数据的URL的文本框
        JTextField url = new JTextField(API.url_now.toString());
        url.setFont(font_TextField);
        url.setBounds(100, 50, 200, 30);
        main_frame.add(url);

        //要发送的数据类型的标签
        JLabel typeLabel = new JLabel("类型：");
        typeLabel.setBounds(48, 90, 70, 30);
        typeLabel.setFont(font_Label);
        main_frame.add(typeLabel);

        //要发送的数据类型的下拉框
        String[] listData = new String[]{"GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE", "TRACE"};
        JComboBox<String> type = new JComboBox<String>(listData);
        type.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 只处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Type = (String) type.getSelectedItem();
                }
            }
        });
        type.setSelectedIndex(0);
        type.setBounds(100, 90, 100, 30);
        main_frame.add(type);

        //要发送的数据的标签
        JLabel textLabel = new JLabel("数据：");
        textLabel.setBounds(48, 130, 70, 30);
        textLabel.setFont(font_Label);
        main_frame.add(textLabel);

        //要发送的数据
        JTextField text = new JTextField();
        text.setBounds(100, 130, 230, 30);
        text.setFont(font_TextField);
        main_frame.add(text);

        main_frame.setVisible(true);
    }
}
