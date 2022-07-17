package com.at.jmmandvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-17
 */
public class VolatileToSee {

//    private static boolean flag = true;
    private static volatile boolean flag = true;

    public static void main(String[] args) {


        new Thread(() -> {

            System.out.println("flag：" + flag);

            while (flag) {

            }

            System.out.println("flag：" + flag);


        }).start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {

            flag = false;

        }).start();


        System.out.println("main thread running...");

    }

}
