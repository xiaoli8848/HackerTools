package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.ManualTools;

import com.HackerTools.WebTools.WebTools.WebTools_ManualTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class request implements WebTools_ManualTools {
    public static int type = 0;
    public static String name = "网站数据发送工具";
    private static final Font font_TitleLabel = new Font("宋体", Font.PLAIN, 25);  //标题字体
    private static final Font font_Label = new Font("宋体", Font.PLAIN, 15);  //标签字体
    private static final Font font_TextField = new Font("宋体", Font.PLAIN, 20); //文本框字体
    private static String Type;
    public static String httpHead = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36 Edg/85.0.564.44";

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
                switch(type.getSelectedIndex()){
                    case 0:
                        text.setEnabled(false);
                        break;
                    default:
                        text.setEnabled(true);
                }
            }
        });
        type.setSelectedIndex(0);
        type.setBounds(100, 90, 100, 30);
        switch(type.getSelectedIndex()){
            case 0:
                text.setEnabled(false);
                break;
            default:
                text.setEnabled(true);
        }
        main_frame.add(type);

        //发送按钮
        JButton sendButton = new JButton("发送");
        sendButton.setBounds(120,200,60,25);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch(type.getSelectedIndex()){
                    case 0:
                        HttpRequest.sendGet(API.url_now.toString(), text.getText(), API);
                        break;
                }
            }
        });
        main_frame.add(sendButton);

        main_frame.setVisible(true);
    }
}

class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, com.HackerTools.WebTools.WebToolsAPI API) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", request.httpHead);
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            //for (String key : map.keySet()) {
            //    System.out.println(key + "--->" + map.get(key));
            //}
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            API.writeLog(2,"发送GET请求出现异常！");
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", request.httpHead);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }

        return result;
    }
}