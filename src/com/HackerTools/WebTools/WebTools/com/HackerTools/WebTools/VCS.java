package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools;

import com.HackerTools.WebTools.WebToolsAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class VCS {
    public static boolean Judge(WebToolsAPI API){
        try {
            URL url1 = new URL(API.url_now + "CVS/Entriesp");
            URL url2 = new URL(API.url_now + ".svn/entriesp");
            if(API.exists(url1.toString()) || API.exists(url2.toString())){
                API.writeLog(1,"发现VCS漏洞");
                return true;
            }else{
                API.writeLog(1,"未发现VCS漏洞");
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean Attack(WebToolsAPI API){
        API.openWebsite(java.net.URI.create(API.url_now + "CVS/Entriesp"));
        API.openWebsite(java.net.URI.create(API.url_now + ".svn/entriesp"));
        API.writeLog(1,"已尝试攻击VCS漏洞");
        return true;
    }
}
