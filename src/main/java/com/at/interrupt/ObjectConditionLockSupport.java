package com.at.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-16
 */
public class ObjectConditionLockSupport {

    static Object object = new Object();

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();


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

    public static void m2(){

        new Thread(() -> {

            //先 唤醒
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

            /*
                程序无法运行
                先await()后signal才OK，否则线程无法被唤醒
             */


            lock.lock();

            try {

                System.out.println(Thread.currentThread().getName() + " come in...");

                condition.await();

                System.out.println(Thread.currentThread().getName() + " 被唤醒");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        },"A").start();



        new Thread(() -> {

            lock.lock();

            try {

                condition.signal();

                System.out.println(Thread.currentThread().getName() + " 发出唤醒通知");

            }finally {
                lock.unlock();
            }

        },"B").start();




    }


    public static void main(String[] args) {

//        m1();

        m2();

    }


}
