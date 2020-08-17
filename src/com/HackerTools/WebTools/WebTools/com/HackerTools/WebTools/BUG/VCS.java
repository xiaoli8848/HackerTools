package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.BUG;

import com.HackerTools.WebTools.WebToolsAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class VCS {
    public static boolean Judge(WebToolsAPI API){
        try {
            URL url1 = new URL(API.url_now + "/CVS/Entriesp");
            URL url2 = new URL(API.url_now + "/.svn/entriesp");
            if(API.exists(url1.toString())){
                API.writeLog(1,"发现 "+ url1.toString() +" 中的VCS漏洞");
                return true;
            }else if(API.exists(url2.toString())){
                API.writeLog(1,"发现 " + url2.toString() + "中的VCS漏洞");
                return false;
            }else{
                API.writeLog(1, "未发现 " + API.url_now.toString() + " 中存在VCS漏洞");
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
        API.writeLog(1,"已尝试攻击" + API.url_now.toString() + "VCS漏洞");
        return true;
    }
}
