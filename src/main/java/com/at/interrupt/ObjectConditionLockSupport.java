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
     *
     *   注释synchronized？？
     *   Object类中的wait、notify、notifyAll用于线程等待和唤醒的方法，都必须在synchronized内部执行（必须用到关键字synchronized）
     */
    public static void m1() {

        new Thread(() -> {

            //尝试先唤醒
//            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            /*
                先 notify 在 wait
                程序卡死，无法唤醒
             */

            //注释 synchronized
//            synchronized (object) {

            /*

                Object类中的wait、notify、notifyAll用于线程等待和唤醒的方法，都必须在synchronized内部执行（必须用到关键字synchronized）

                A come in...
                Exception in thread "B" Exception in thread "A" java.lang.IllegalMonitorStateException
                    at java.lang.Object.notify(Native Method)
                    at com.at.interrupt.ObjectConditionLockSupport.lambda$m1$1(ObjectConditionLockSupport.java:56)
                    at java.lang.Thread.run(Thread.java:748)
                java.lang.IllegalMonitorStateException
                    at java.lang.Object.wait(Native Method)
                    at java.lang.Object.wait(Object.java:502)
                    at com.at.interrupt.ObjectConditionLockSupport.lambda$m1$0(ObjectConditionLockSupport.java:38)
                    at java.lang.Thread.run(Thread.java:748)
             */

                System.out.println(Thread.currentThread().getName() + " come in...");

                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " 被唤醒");


//            }


        }, "A").start();


        new Thread(() -> {

//            synchronized (object) {

                object.notify();

                System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

//            }

        }, "B").start();


    }


    public static void main(String[] args) {

        m1();

    }


}
