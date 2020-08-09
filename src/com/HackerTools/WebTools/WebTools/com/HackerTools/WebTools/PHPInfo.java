package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools;
import com.HackerTools.WebTools.WebToolsAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class PHPInfo {
    public static void main(String[] args){

    }

    public static boolean Judge(WebToolsAPI API){
        try {
            URL url = new URL(API.url_now + "PHPInfo()");
            if(API.exists(url.toString())){
                API.writeLog("发现PHPInfo漏洞");
                return true;
            }else{
                API.writeLog("未发现PHPInfo漏洞");
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean Attack(WebToolsAPI API){
        API.openWebsite(java.net.URI.create(API.url_now + "PHPInfo()"));
        API.writeLog("已尝试攻击PHPInfo漏洞");
        return true;
    }
}
