package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools.BUG;

import com.HackerTools.WebTools.WebTools.WebTools_BUG;
import com.HackerTools.WebTools.WebToolsAPI;

import java.net.MalformedURLException;
import java.net.URL;

public class VCS implements WebTools_BUG {
    public static int type = 1;

    public int Judge(WebToolsAPI API) {
        try {
            URL url1 = new URL(API.url_now + "/CVS/Entriesp");
            URL url2 = new URL(API.url_now + "/.svn/entriesp");
            if (API.exists(url1.toString())) {
                API.writeLog(1, "发现 " + url1.toString() + " 中的VCS漏洞");
                return 1;
            } else if (API.exists(url2.toString())) {
                API.writeLog(1, "发现 " + url2.toString() + "中的VCS漏洞");
                return 2;
            } else {
                API.writeLog(1, "未发现 " + API.url_now.toString() + " 中存在VCS漏洞");
                return 0;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public int Attack(WebToolsAPI API, int type) {
        switch (type) {
            case 0:
                return 0;
            case 1:
                API.openWebsite(java.net.URI.create(API.url_now + "/CVS/Entriesp"));
                break;
            case 2:
                API.openWebsite(java.net.URI.create(API.url_now + "/.svn/entriesp"));
                break;
        }
        API.writeLog(1, "已尝试攻击" + API.url_now.toString() + "的VCS漏洞");
        return 1;
    }
}
