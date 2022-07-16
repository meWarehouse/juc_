package com.at.interrupt;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @create 2022-07-16
 */
public class ThreadCustomizedCommunication {

    /*

        多线程之间按顺序调用，实现A->B->C。三个线程启动，要求如下：
            AA打印5次，BB打印10次，CC打印15次
            接着
            AA打印5次，BB打印10次，CC打印15次
            打印10轮


            synchronize 无法做到


     */



    public static void main(String[] args) {

//
//        ConditionShareResource resource = new ConditionShareResource();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print5();
//            }
//
//        }, "A").start();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print10();
//            }
//
//        }, "B").start();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print15();
//            }
//
//        }, "C").start();
//
//        LockSupportShareResource resource = new LockSupportShareResource();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print5();
//            }
//
//        }, "A").start();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print10();
//            }
//
//        }, "B").start();
//
//        new Thread(() -> {
//
//            for (int i = 0; i < 5; i++) {
//                resource.print15();
//            }
//
//        }, "C").start();
//
//        String name = Thread.currentThread().getName();

    }


}

class LockSupportShareResource{

    private volatile int flag = 1;

    public void print5(){

        try {

            while (flag != 1){
                LockSupport.park();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 2;
//            LockSupport.unpark(name);


        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void print10(){

        try {

            while (flag != 2){
                LockSupport.park();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 3;
//            LockSupport.unpark(name);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void print15(){

        try {

            while (flag != 3){
                LockSupport.park();
            }

            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 1;
//            LockSupport.unpark(name);


        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

class ConditionShareResource {

    private Lock lock = new ReentrantLock();

    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    private volatile int flag = 1;

//    public Condition getCondition(String ThreadName){
//
//        switch (ThreadName){
//            case "A": return c1;
//            case "B": return c2;
//            case "C": return c3;
//            default: return null;
//        }
//    }
//
//    public int getFlag(String ThreadName){
//        switch (ThreadName){
//            case "A": return 1;
//            case "B": return 2;
//            case "C": return 3;
//            default: return 0;
//        }
//    }
//
//
//    public void printX(String ThreadName){
//
//        lock.lock();
//
//        try {
//
//            Condition condition = getCondition(ThreadName);
//            flag = getFlag(ThreadName);
//
//            while (flag)
//
//
//
//        }finally {
//            lock.unlock();
//        }
//
//    }


    public void print5() {

        lock.lock();

        try {

            while (flag != 1) {
                c1.await();
            }

            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 2;

            c2.signal();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void print10() {

        lock.lock();

        try {

            while (flag != 2) {
                c2.await();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 3;

            c3.signal();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


    public void print15() {

        lock.lock();

        try {

            while (flag != 3) {
                c3.await();
            }

            for (int i = 0; i < 15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + (i + 1));
            }

            flag = 1;

            c1.signal();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}
