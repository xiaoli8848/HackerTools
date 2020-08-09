package com.HackerTools.WebTools.WebTools.com.HackerTools.WebTools;

/*******************************************************************************
 * @project: OpenExplorer
 * @package: com.burns.test
 * @file: OpenExplorer.java
 * @author: Administrator
 * @created: 2017-9-8
 * @purpose:
 *
 * @version: 1.0
 *
 * Revision History at the end of file.
 *
 * Copyright 2017 AcconSys All rights reserved.
 ******************************************************************************/

/**
 * java调用系统默认浏览器打开链接
 *
 * @author Administrator
 */
public class OpenWebsite {
    public static void main(String args[]) {

    }

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

/*******************************************************************************
 * <B>Revision History</B><BR>
 * [type 'revision' and press Alt + / to insert revision block]<BR>
 *
 *
 *
 * Copyright 2017 AcconSys All rights reserved.
 ******************************************************************************/