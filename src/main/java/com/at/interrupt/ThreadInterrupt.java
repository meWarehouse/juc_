package com.at.interrupt;


import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-10
 */
public class ThreadInterrupt {

    private static volatile boolean isStop = false;


    public static void m1(String[] args) {

        new Thread(() -> {

            while (true){
                if(isStop){
                    System.out.println(String.format("... %s 线程被中断",Thread.currentThread().getName()));
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " running...");
            }

        },"A").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
            System.out.println(Thread.currentThread().getName() + " 将 A 线程中断");
        },"B").start();

        System.out.println("main thread running.....");



    }


}
