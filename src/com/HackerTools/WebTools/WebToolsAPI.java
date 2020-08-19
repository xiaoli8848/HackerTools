package com.HackerTools.WebTools;

import com.HackerTools.WebTools.IP.getIP;
import com.HackerTools.WebTools.WebTools.WebTools_BUG;

import java.net.*;

public class WebToolsAPI extends com.HackerTools.WebTools.IP.getIP{
    public InetAddress ip_now;
    public URL url_now;

    public WebToolsAPI(String url, int port) {
        try {
            this.ip_now = new getIP().getIP_byURL(url);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            this.url_now = new URL("http://" + url + ":" + port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public WebToolsAPI(String url) {
        try {
            this.ip_now = new getIP().getIP_byURL(url);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            this.url_now = new URL("http://" + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void writeLog(int type, String text) {
        com.HackerTools.WebTools.WebToolsManager.Log_Append(type, text);
    }

    public void writeLog(String text) {
        com.HackerTools.WebTools.WebToolsManager.Log_Append(text);
    }

    public void openWebsite(java.net.URI uri) {
        OpenWebsite.OpenWebsite(uri);
    }

    public boolean exists(String url) {
        try {

            //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。

            HttpURLConnection.setFollowRedirects(false);

            //到 URL 所引用的远程对象的连接

            HttpURLConnection con = (HttpURLConnection) new URL(url)

                    .openConnection();

            /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/

            con.setRequestMethod("HEAD");
            con.setConnectTimeout(1500);
            con.setReadTimeout(1500);
            //从 HTTP 响应消息获取状态码
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (java.lang.Throwable e){
            return false;
        }
    }

}

class OpenWebsite {
    public static void OpenWebsite(java.net.URI url) {
        if (java.awt.Desktop.isDesktopSupported()) {
            try {
                // 获取当前系统桌面扩展
                java.awt.Desktop dp = java.awt.Desktop.getDesktop();
                // 判断系统桌面是否支持要执行的功能
                if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    // 获取系统默认浏览器打开链接
                    dp.browse(url);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}