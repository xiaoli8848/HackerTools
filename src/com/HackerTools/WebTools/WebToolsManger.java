package com.HackerTools.WebTools;

import com.HackerTools.WebTools.IPLoc.IPSeeker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.io.File;

import javax.swing.JFrame;

/**
 * 程序主类，负责UI配置。
 *
 * @author XiaoLi8848
 */
public class WebToolsManger {
    static double size = 1.0;  //主窗体及控件大小缩放倍数
    static int frame_width = (int) (1000 * size);   //主窗体默认宽度
    static int frame_height = (int) (1000 * size);  //主窗体默认高度
    static String frame_Title = "WebToolsManger";   //窗体标题
    static Font font_TitleLabel = new Font("宋体", Font.PLAIN, 30);  //标题字体
    static Font font_Label = new Font("宋体", Font.PLAIN, 25);  //标签字体
    static Font font_TextField = new Font("宋体", Font.PLAIN, 28); //文本框字体

    public static void main(String[] args) {

        //创建主窗体
        JFrame main_Frame = new JFrame(frame_Title);
        main_Frame.setLayout(null); //使用绝对布局器
        main_Frame.setSize(frame_width, frame_height);
        main_Frame.setBackground(Color.WHITE);
        main_Frame.setResizable(false); //禁止最大化或拉伸窗体

        //创建程序标题标签
        JLabel titleLabel = new JLabel(frame_Title, JLabel.CENTER);    //标题文字
        titleLabel.setFont(font_TitleLabel);
        titleLabel.setBounds((frame_width / 2) - 150, 15, (int) (300 * size), (int) (40 * size));
        main_Frame.add(titleLabel);

        //创建IP地址框输入标签
        JLabel ipTextFieldLabel = new JLabel("IP/HostName/URL :");
        ipTextFieldLabel.setFont(font_Label);
        ipTextFieldLabel.setBounds(15, 60, 250, 50);
        main_Frame.add(ipTextFieldLabel);

        //创建IP地址输入框
        JTextField ipTextField = new JTextField("http://www.baidu.com");
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
                try {
                    finalIpLabel.setText(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress());
                    finalIpLocLabel.setText(new getIP().getIPLoc(new getIP().getIP_byURL(ipTextField.getText()).getHostAddress()));
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                }
            }
        });
        ipLabel.setText(finalIpLabel.getText());
        ipLocLabel.setText(finalIpLocLabel.getText());
        main_Frame.add(ipParseButton);

        main_Frame.setVisible(true); //显示主窗体

    }
}

/**
 * IP解析类，提供方法以试图解析出IP地址。
 *
 * @author XiaoLi8848
 */
class getIP {
    /**
     * IP解析错误类，在解析IP中出现错误时抛出。
     *
     * @author XiaoLi8848
     * retCd（msgDes）：0（未知错误）；1（提供的URL或主机名非法）；2（网络错误）;3（文件读写错误）
     */
    public class IP_parse_Exception extends RuntimeException {

        private int retCd;  //异常对应的返回码
        private String msgDes;  //异常对应的描述信息

        public IP_parse_Exception(int retCd, String msgDes) {
            super(msgDes);
            this.retCd = retCd;
            this.msgDes = msgDes;
        }

        public int getRetCd() {
            return retCd;
        }

        public String getMsgDes() {
            return msgDes;
        }
    }

    public InetAddress getIP_byURL(String surl) throws UnknownHostException {
        /**
         * 从所给的主机名或URL解析IP地址
         * @param surl：String类型变量，表示一个URL或者一个主机名。
         * @return 返回一个String类型变量，表示试图将提供的URL或主机名解析为IP地址的结果。
         * @exception IP_parse_Exception：可能的错误码有1,2
         * @author XiaoLi8848
         * @Time 2020-7-17 8:50
         */
        try {
            URL url = new URL(surl); //实例化一个url对象，存储输入的url字符串
            return InetAddress.getByName(url.getHost());  //先通过getHost获取url中的主机名，然后将主机名解析为ip
        } catch (MalformedURLException e) {
            try {
                return InetAddress.getByName(surl); //如果直接解析出错，说明参数可能不是URL，可能是主机名
            } catch (UnknownHostException r) {  //直接解析和提取主机名解析都出错，则可能是网络错误或参数无效
                try {
                    InetAddress.getByName("www.baidu.com"); //通过解析www.baidu.com，检测网络是否正常
                } catch (UnknownHostException o) {
                    throw new IP_parse_Exception(2, "网络错误");
                }
                throw new IP_parse_Exception(1, "提供的URL或主机名非法");
            }
        }
    }

    public FileInputStream getIP_byFile(String filename) {
        /**
         * 从所给的txt文件的文件名解析IP地址，txt文件中有若干行一个一行的URL或主机名，方法将试图2解析txt中的每个URL或主机名为IP地址
         * @param filename：String类型变量，表示存有主机名或URL的txt文件
         * @return 返回一个FileInputStream，指向表示结果的txt文件。
         * @exception IP_parse_Exception：可能的错误码有1,2,3
         * @author XiaoLi8848
         * @Time 2020-7-17 10:13
         */
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String fn = filename;
            int i = filename.length();//获取所给的文件名的所在路径
            while (fn.charAt(i) != '/') {
                i--;
            }
            fn = fn.substring(i);
            File writename = new File(fn);
            writename.createNewFile();
            BufferedWriter w = new BufferedWriter(new FileWriter(writename));
            String ip = "";
            while (ip != null) {
                ip = r.readLine();
                w.write(getIP_byURL(ip).getHostAddress());
            }
            w.flush();
            w.close();
            return new FileInputStream(new File(fn));
        } catch (IOException e) {
            throw new IP_parse_Exception(3, "文件读写错误");
        }
    }

    public InetAddress getIP_byLocal() {
        /**
         * 返回本机IP地址
         * @return 返回一个InetAddress变量，表示本机IP地址
         * @exception IP_parse_Exception：可能的错误码有0,2
         * @author XiaoLi8848
         * @Time 2020-7-17 10:17
         */
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            try {
                InetAddress.getByName("www.baidu.com"); //通过解析www.baidu.com，检测网络是否正常
            } catch (UnknownHostException o) {
                throw new IP_parse_Exception(2, "网络错误");
            }
            throw new IP_parse_Exception(0, "未知错误");
        }
    }

    public String getIPLoc(String ip) {
        /**
         * 返回所给的IP地址的归属地
         * @return 返回一个String变量，表示所给的IP地址的归属地
         * @author XiaoLi8848
         * @Time 2020-7-18 11:44
         */
        IPSeeker seeker = IPSeeker.getInstance();
        return seeker.getAddress(ip);
    }
}