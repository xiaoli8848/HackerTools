package com.HackerTools.WebTools;

import com.HackerTools.WebTools.IP.getIP;
import com.HackerTools.WebTools.WebTools.WebTools_BUG;
import com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.BUG.PHPInfo;
import com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.BUG.VCS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import java.nio.file.attribute.FileTime;

import static com.HackerTools.WebTools.ClassLoader.GetClass;

/**
 * 程序主类，负责UI配置。
 *
 * @author XiaoLi8848
 */
public class WebToolsManager {
    private static double size = 1.0;  //主窗体及控件大小缩放倍数
    private static int frame_width = (int) (1000 * size);   //主窗体默认宽度
    private static int frame_height = (int) (1000 * size);  //主窗体默认高度
    private static String frame_Title = "WebToolsManager - 主菜单";   //窗体标题
    static Font font_TitleLabel = new Font("宋体", Font.PLAIN, 30);  //标题字体
    static Font font_Label = new Font("宋体", Font.PLAIN, 25);  //标签字体
    static Font font_TextField = new Font("宋体", Font.PLAIN, 28); //文本框字体
    static JFrame main_Frame = new JFrame(frame_Title); //创建主窗体
    private static JTextField ipTextField = new JTextField("http://www.baidu.com"); //创建IP地址输入框
    private static JTextArea LogText = new JTextArea(7, 32);    //创建日志文本框
    private static final String VERSION_IP_LOC_DATABASE = "2020年 08月 10日 星期一 22:11:56 CST"; //IP地理位置数据库更新时间

    public static void main(String[] args) {
        main_Frame.setLayout(null); //使用绝对布局器
        main_Frame.setSize(frame_width, frame_height);
        main_Frame.setBackground(Color.WHITE);
        main_Frame.setResizable(false); //禁止最大化或拉伸窗体

        //创建程序标题标签
        JLabel titleLabel = new JLabel(frame_Title, JLabel.CENTER);    //标题文字
        titleLabel.setFont(font_TitleLabel);
        titleLabel.setBounds((frame_width / 2) - 250, 15, (int) (500 * size), (int) (40 * size));
        main_Frame.add(titleLabel);

        //创建IP地址框输入标签
        JLabel ipTextFieldLabel = new JLabel("IP/HostName/URL :");
        ipTextFieldLabel.setFont(font_Label);
        ipTextFieldLabel.setBounds(15, 60, 250, 50);
        main_Frame.add(ipTextFieldLabel);

        //IP地址输入框
        ipTextField.setFont(font_TextField);
        ipTextField.setBounds(240, 70, (int) (350 * size), (int) (30 * size));
        main_Frame.add(ipTextField);

        //创建IP地址显示标签
        JLabel ipSetLabel = new JLabel("IP :");
        ipSetLabel.setFont(font_Label);
        ipSetLabel.setBounds(15, 100, 200, 50);
        main_Frame.add(ipSetLabel);

        //创建IP地址标签
        JLabel ipLabel = null;
        try {
            ipLabel = new JLabel(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipLabel.setFont(font_Label);
        ipLabel.setBounds(65, 100, 200, 50);
        main_Frame.add(ipLabel);

        //创建IP地理位置显示标签
        JLabel ipLocSetLabel = new JLabel("IPLoc :");
        ipLocSetLabel.setFont(font_Label);
        ipLocSetLabel.setBounds(15, 140, 200, 50);
        main_Frame.add(ipLocSetLabel);

        //创建IP地理位置标签
        JLabel ipLocLabel = null;
        try {
            ipLocLabel = new JLabel(new getIP().getIPLoc(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipLocLabel.setFont(font_Label);
        ipLocLabel.setBounds(100, 140, 400, 50);
        main_Frame.add(ipLocLabel);

        //创建IP地址解析按钮
        JButton ipParseButton = new JButton("Parse");
        ipParseButton.setFont(font_Label);
        ipParseButton.setBounds(620, 70, (int) (100 * size), (int) (30 * size));
        JLabel finalIpLabel = ipLabel;
        JLabel finalIpLocLabel = ipLocLabel;
        ipParseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                /*try {
                    if(!new WebToolsAPI("").exists(ipTextField.getText()) && new getIP().getIP_byURL("http://" + ipTextField.getText())==null){    //创建一个url为空的API实例，判断输入的数据的有效性
                        Log_Append(5,"无法访问该URL或该主机名或该IP");
                        return;
                    }
                } catch (UnknownHostException unknownHostException) {
                    Log_Append(5,"无法访问该URL或该主机名或该IP");
                }*/
                try {
                    if(!new WebToolsAPI("").exists(new URL(ipTextField.getText()).toString())){
                        Log_Append(3,"无法访问该URL或该主机名或该IP");
                    }
                } catch (MalformedURLException malformedURLException) {
                    try {
                        if(!new WebToolsAPI("").exists(new URL("http://" + ipTextField.getText()).toString())){
                            Log_Append(3,"无法访问该URL或该主机名或该IP");
                        }
                    } catch (MalformedURLException urlException) {
                        Log_Append(3,"该URL或该主机名或该IP无效");
                    }
                }
                try {
                    try {
                        finalIpLabel.setText(new getIP().getIP_byURL(new URL(ipTextField.getText()).getHost()).getHostAddress());
                    } catch (MalformedURLException malformedURLException) {
                        try {
                            finalIpLabel.setText(new getIP().getIP_byURL(new URL("http://" + ipTextField.getText()).getHost()).getHostAddress());
                        } catch (MalformedURLException urlException) {
                            urlException.printStackTrace();
                        }
                    }
                    try {
                        finalIpLocLabel.setText(new getIP().getIPLoc(new getIP().getIP_byURL(new URL(ipTextField.getText()).getHost()).getHostAddress()));
                    } catch (MalformedURLException malformedURLException) {
                        try {
                            finalIpLocLabel.setText(new getIP().getIPLoc(new getIP().getIP_byURL(new URL("http://" + ipTextField.getText()).getHost()).getHostAddress()));
                        } catch (MalformedURLException urlException) {
                            urlException.printStackTrace();
                        }
                    }
                    try {
                        if (new URL(ipTextField.getText()).getPort() == -1) { //如果没有端口号
                            ParseWebsite(finalIpLabel.getText());
                        } else {
                            ParseWebsite(finalIpLabel.getText(), new URL(ipTextField.getText()).getPort());
                        }
                    } catch (MalformedURLException malformedURLException) {
                        try {
                            if (new URL("http://" + ipTextField.getText()).getPort() == -1) { //如果没有端口号
                                ParseWebsite(finalIpLabel.getText());
                            } else {
                                ParseWebsite(finalIpLabel.getText(), new URL(ipTextField.getText()).getPort());
                            }
                        } catch (MalformedURLException urlException) {
                            try {
                                ParseWebsite(finalIpLabel.getText(), new URL("http://" + ipTextField.getText()).getPort());
                            } catch (MalformedURLException exception) {
                                exception.printStackTrace();
                            }
                        }
                    }
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();

                }
            }
        });
        ipLabel.setText(finalIpLabel.getText());
        ipLocLabel.setText(finalIpLocLabel.getText());
        main_Frame.add(ipParseButton);

        //创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("文件");
        JMenu menuTools = new JMenu("工具");
        JMenuItem menuExit = new JMenuItem("关闭");
        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(menuExit);
        menuBar.add(menuFile);
        menuBar.add(menuTools);
        main_Frame.setJMenuBar(menuBar);

        //日志栏
        LogText.setFont(font_Label);
        LogText.setBounds(15, 400, 965, 500);
        LogText.setLineWrap(true);
        main_Frame.add(LogText);

        main_Frame.setVisible(true); //显示主窗体

        //单独写入主窗体载入的日志，以免发生首行为空的BUG
        Date date = new Date();
        String time_now = dateToString(date);
        LogText.append('[' + time_now + ']' + " " + "主窗体加载完毕");

        try {
            File file = new File("qqwry.dat");
            Long lastModified = file.lastModified();
            Date file_date = new Date(lastModified);
            Log_Append(1, "成功载入IP地址库，数据库版本为：" + VERSION_IP_LOC_DATABASE);
        } catch (java.lang.Throwable e) {
            Log_Append(5, "载入IP地址库失败");
        }
    }

    private static void ParseWebsite(String url, int port) {
        /**
         * 暂时的调用工具的方法
         * @author XiaoLi8848
         * @Time 2020-8-14 18:17
         */
        WebToolsAPI API = new WebToolsAPI(url, port);
        java.util.List<Class> c = GetClass("com.HackerTools.WebTools.WebTools.WebTools_BUG");
        for(int i=0;i<c.size();i++){
            Class cl = c.get(i);
            try {
                try {
                    WebTools_BUG cs = (WebTools_BUG)Class.forName(cl.getName()).newInstance();
                    cs.Attack(API,cs.Judge(API));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
            }
            //TODO 实例化类并调用Judge、Attack方法
        }
    }

    private static void ParseWebsite(String url) {
        /**
         * 暂时的调用工具的方法
         * @author XiaoLi8848
         * @Time 2020-8-14 18:17
         */
        WebToolsAPI API = new WebToolsAPI(url);
        java.util.List<Class> c = GetClass("com.HackerTools.WebTools.WebTools.WebTools_BUG");
        for(int i=0;i<c.size();i++){
            Class cl = c.get(i);
            try {
                try {
                    WebTools_BUG cs = (WebTools_BUG)Class.forName(cl.getName()).newInstance();
                    cs.Attack(API,cs.Judge(API));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (ClassNotFoundException e) {
            }
            //TODO 实例化类并调用Judge、Attack方法
        }
    }

    public static void Log_Append(int type, String text) {
        /**
         * 添加日志记录
         * @author XiaoLi8848
         * @Time 2020-7-14 18:18
         */
        Date date = new Date();
        String time_now = dateToString(date);
        LogText.append("\n");

        switch (type) {
            case 1:
                LogText.append('[' + time_now + ']' + " Info: " + text);
                break;
            case 2:
                LogText.append('[' + time_now + ']' + " Error: " + text);
                break;
            case 3:
                LogText.append('[' + time_now + ']' + " Warning: " + text);
                break;
            case 4:
                LogText.append('[' + time_now + ']' + " Succeed: " + text);
                break;
            case 5:
                LogText.append('[' + time_now + ']' + " Fail: " + text);
                break;
            case 0:
            default:
                LogText.append('[' + time_now + ']' + " " + text);
                break;
        }
    }

    public static void Log_Append(String text) {
        Log_Append(0, text);
    }

    private static String dateToString(Date date) {
        /**
         * 格式化日期
         * @return 返回一个String，表示格式化后的日期
         * @author
         * @Time 2020-8-14 18:18
         */
        String str = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(str);
        String dateFormat = format.format(date);
        return dateFormat;
    }

}

// TODO：使用本类读取当前IP地址数据库的修改时间，实现自动检测数据库版本

class FileCreationDate {
    public static String FileCreationTime(File file) {
        BasicFileAttributes attrs;
        try {
            attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            FileTime time = attrs.creationTime();

            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String formatted = simpleDateFormat.format(new Date(time.toMillis()));
            return formatted;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
