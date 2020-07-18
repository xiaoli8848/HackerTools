/*
 *摘抄自：https://blog.csdn.net/zshwlw/article/details/1745440
 */
package com.HackerTools.WebTools;

import java.util.List;
import static com.sun.javafx.scene.control.skin.Utils.getResource;

/**
 * @author LJ-silver
 */
public class Test {
    public static void main(String[] args) {
        IPSeeker seeker = IPSeeker.getInstance();
        System.out.println(seeker.getAddress("127.0.0.1"));
    }
}