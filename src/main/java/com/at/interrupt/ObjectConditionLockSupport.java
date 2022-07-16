package com.at.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-16
 */
public class ObjectConditionLockSupport {

    static Object object = new Object();

    /**
     * synchronized
     *  wait
     *  notify

     */
    public static void m1() {

        new Thread(() -> {

            synchronized (object) {

                System.out.println(Thread.currentThread().getName() + " come in...");

                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 被唤醒");


            }


        }, "A").start();


        new Thread(() -> {

            synchronized (object) {

                object.notify();

                System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

            }

        }, "B").start();


    }


    public static void main(String[] args) {

        m1();

    }


}
