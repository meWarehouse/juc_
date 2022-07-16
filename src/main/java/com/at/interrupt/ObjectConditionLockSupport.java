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
     *
     *  尝试先唤醒 ？？？？
     *  先wait后notify才OK
     *

     */
    public static void m1() {

        new Thread(() -> {

            //尝试先唤醒
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            /*
                先 notify 在 wait
                程序卡死，无法唤醒
             */

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
