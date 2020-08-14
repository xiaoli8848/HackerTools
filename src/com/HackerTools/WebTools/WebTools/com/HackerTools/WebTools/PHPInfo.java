package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools;
import com.HackerTools.WebTools.WebToolsAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class PHPInfo {
    public static boolean Judge(WebToolsAPI API){
        try {
            URL url1 = new URL(API.url_now + "phpinfo.php");
            URL url2 = new URL(API.url_now + "test.php");
            if(API.exists(url1.toString()) || API.exists(url2.toString())){
                API.writeLog(1,"发现PHPInfo漏洞");
                return true;
            }else{
                API.writeLog(1,"未发现PHPInfo漏洞");
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean Attack(WebToolsAPI API){
        API.openWebsite(java.net.URI.create(API.url_now + "phpinfo.php"));
        API.openWebsite(java.net.URI.create(API.url_now + "test.php"));
        API.writeLog(1,"已尝试攻击PHPInfo漏洞");
        return true;
    }
}
