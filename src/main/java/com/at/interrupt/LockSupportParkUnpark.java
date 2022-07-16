package com.at.interrupt;

import java.util.IdentityHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @create 2022-07-16
 */
public class LockSupportParkUnpark {

    public static void main(String[] args) {


//        m1();

//        m2();

        m3();



    }

    public static void m3(){


        // 一个锅可以有多个锅盖，一个锅盖只能扣一个锅

        Thread A = new Thread(() -> {

            System.out.println(Thread.currentThread().getName() + " come in...");

            LockSupport.park();
            LockSupport.park();
            LockSupport.park();
            LockSupport.park();

            System.out.println(Thread.currentThread().getName() + " 被唤醒");

        }, "A");

        A.start();

        try { TimeUnit.MILLISECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {

            LockSupport.unpark(A);
//            LockSupport.unpark(A);
//            LockSupport.unpark(A);

            System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

        },"B").start();

    }

    public static void m2(){



        Thread A = new Thread(() -> {


            System.out.println(Thread.currentThread().getName() + " come in...");




            LockSupport.park();

            // 不能 打断正在休眠的线程 sleep join wait
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
/*
A come in...
B 发出唤醒通知
A 被唤醒
java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at com.at.interrupt.LockSupportDemo.lambda$m2$0(LockSupportDemo.java:38)
	at java.lang.Thread.run(Thread.java:748)

*/


            System.out.println(Thread.currentThread().getName() + " 被唤醒");



        }, "A");

        A.start();

        //确保 A 先执行
        try { TimeUnit.MILLISECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {

            //唤醒 线程 A
            LockSupport.unpark(A);
            A.interrupt();

            System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

        },"B").start();

    }

    public static void m1(){

        Thread A = new Thread(() -> {

            System.out.println(Thread.currentThread().getName() + " come in...");

            // 中断
            LockSupport.park();

            System.out.println(Thread.currentThread().getName() + " 被唤醒");


        }, "A");

        A.start();


        new Thread(() -> {

            //唤醒 线程 A
            LockSupport.unpark(A);

            System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

        },"B").start();
    }

}
