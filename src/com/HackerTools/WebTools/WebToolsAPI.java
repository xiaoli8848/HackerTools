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

    public void writeLog(int type, String text) {
        com.HackerTools.WebTools.WebToolsManager.Log_Append(type, text);
    }

    public void writeLog(String text) {
        com.HackerTools.WebTools.WebToolsManager.Log_Append(text);
    }

    public void openWebsite(java.net.URI uri) {
        OpenWebsite.OpenWerbsite(uri);
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

            //从 HTTP 响应消息获取状态码

            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }

}

class OpenWebsite {
    public static void OpenWerbsite(java.net.URI url) {
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