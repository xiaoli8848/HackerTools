package com.HackerTools.WebTools;

import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

public class WebToolsAPI extends com.HackerTools.WebTools.IP.getIP{
    public InetAddress ip_now;
    public URL url_now;

    public WebToolsAPI(InetAddress ip) {
        this.ip_now = ip;
        try {
            this.url_now = new URL("http://" + ip_now.getHostAddress() + "/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void writeLog(String Log) {

    }

    public void openWebsite(URL url) {

    }

    public boolean exists(String URLName) {
        try {

            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。

            HttpURLConnection.setFollowRedirects(false);

            //到 URL 所引用的远程对象的连接

            HttpURLConnection con = (HttpURLConnection) new URL(URLName)

                    .openConnection();

            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/

            con.setRequestMethod("HEAD");

            //从 HTTP 响应消息获取状态码

            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }

}

