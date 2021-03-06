package com.HackerTools.WebTools.IP;

import com.HackerTools.WebTools.IPLoc.IPSeeker;

import java.io.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * IP解析类，提供方法以试图解析出IP地址。
 *
 * @author XiaoLi8848
 */
public class getIP {
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
         * @exception getIP.IP_parse_Exception ：可能的错误码有1,2
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
                    throw new getIP.IP_parse_Exception(2, "网络错误");
                }
                try {
                    return InetAddress.getByName(surl);
                }catch(UnknownHostException er){
                    throw new getIP.IP_parse_Exception(1, "提供的URL或主机名非法");
                }
            }
        }
    }

    public FileInputStream getIP_byFile(String filename) {
        /**
         * 从所给的txt文件的文件名解析IP地址，txt文件中有若干行一个一行的URL或主机名，方法将试图2解析txt中的每个URL或主机名为IP地址
         * @param filename：String类型变量，表示存有主机名或URL的txt文件
         * @return 返回一个FileInputStream，指向表示结果的txt文件。
         * @exception getIP.IP_parse_Exception ：可能的错误码有1,2,3
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
            throw new getIP.IP_parse_Exception(3, "文件读写错误");
        }
    }

    public InetAddress getIP_byLocal() {
        /**
         * 返回本机IP地址
         * @return 返回一个InetAddress变量，表示本机IP地址
         * @exception getIP.IP_parse_Exception ：可能的错误码有0,2
         * @author XiaoLi8848
         * @Time 2020-7-17 10:17
         */
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            try {
                InetAddress.getByName("www.baidu.com"); //通过解析www.baidu.com，检测网络是否正常
            } catch (UnknownHostException o) {
                throw new getIP.IP_parse_Exception(2, "网络错误");
            }
            throw new getIP.IP_parse_Exception(0, "未知错误");
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
