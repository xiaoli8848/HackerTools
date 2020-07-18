package com.HackerTools.WebTools;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 程序主类，负责UI配置。
 *
 * @author XiaoLi8848
 */
public class WebToolsManger {
    static double size = 1.0;  //窗体及空间大小缩放倍数
    static int frame_width = (int) (600 * size);
    static int frame_height = (int) (800 * size);

    public static void main(String[] args) {
        JFrame f = new JFrame("第一个Swing窗体");
        f.setSize(frame_width, frame_height);
        //imension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //f.setSize((int)(Double.valueOf(screenSize.width)*0.5), (int)(Double.valueOf(screenSize.height)*0.5));
        f.setBackground(Color.WHITE);  // 将背景设置成白色
        f.setVisible(true);            // 让组件可见
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
}