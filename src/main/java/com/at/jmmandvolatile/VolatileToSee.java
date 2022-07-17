package com.at.jmmandvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-17
 */
public class VolatileToSee {

//    private static boolean flag = true; // 不加volatile，没有可见性，程序无法停止
    private static volatile boolean flag = true; // 加了volatile，保证可见性，程序可以停止

    public static void main(String[] args) {


        new Thread(() -> {

            System.out.println("flag：" + flag);

            while (flag) {

            }

            System.out.println("flag：" + flag);


        },"t1").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(() -> {

            flag = false;

        },"t2").start();


        System.out.println("main thread running...");

    }


    /*
        线程t1中为何看不到被t2线程修改为false的flag的值？

        问题可能:
            1. t2线程修改了flag之后没有将其刷新到主内存，所以t1线程看不到。
            2. t2线程将flag刷新到了主内存，但是t1一直读取的是自己工作内存中flag的值，没有去主内存中更新获取flag最新的值。

        诉求：
            1.线程中修改了工作内存中的副本之后，立即将其刷新到主内存；
            2.工作内存中每次读取共享变量时，都去主内存中重新读取，然后拷贝到工作内存。

        解决：
            使用volatile修饰共享变量，就可以达到上面的效果，被volatile修改的变量有以下特点：
                1. 线程中读取的时候，每次读取都会去主内存中读取共享变量最新的值，然后将其复制到工作内存
                2. 线程中修改了工作内存中变量的副本，修改之后会立即刷新到主内存
     */

}
