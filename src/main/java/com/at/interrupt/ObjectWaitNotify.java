package com.at.interrupt;

/**
 * @create 2022-07-16
 */
public class ObjectWaitNotify {

/*

现在两个线程
作初始值为零的一个变量
实现一个线程对该变量加1，一个线程对该变量减1
交替，来10轮


 线程    操作    资源类
 高内聚  低耦合

 判断    干活   通知

虚假唤醒 用 while


 */

    public static void main(String[] args) {

        Resource resource = new Resource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.desc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();


        // 两个 线程交替操作没有问题


        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    resource.desc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();



    }


}


class Resource {

    private int shareResource = 0;


    // +1
    public synchronized void inc() throws InterruptedException {

        // 判断
//        if (shareResource != 0) {
            // 等待

/*
Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object.
 In other words, this method behaves exactly as if it simply performs the call wait(0).
The current thread must own this object's monitor. The thread releases ownership of this monitor and waits until another
thread notifies threads waiting on this object's monitor to wake up either through a call to the notify method or the notifyAll method.
The thread then waits until it can re-obtain ownership of the monitor and resumes execution.
As in the one argument version, interrupts and spurious wakeups are possible, and this method should always be used in a loop:
           synchronized (obj) {
               while (<condition does not hold>)
                   obj.wait();
               ... // Perform action appropriate to condition
           }

This method should only be called by a thread that is the owner of this object's monitor.
See the notify method for a description of the ways in which a thread can become the owner of a monitor.
*/

//            wait();
//        }


        while (shareResource != 0){

            this.wait();

        }

        // 干活
        shareResource++;

        System.out.println(Thread.currentThread().getName() + " -> " + shareResource);

        //通知
        this.notifyAll();

    }


    // -1
    public synchronized void desc() throws InterruptedException {

//        //判断
//        if (shareResource != 1) {
//            // 等待
//            wait();
//        }

        while (shareResource != 1){

            this.wait();

        }

        // 干活
        shareResource --;

        System.out.println(Thread.currentThread().getName() + " -> " + shareResource);


        //通知
        this.notifyAll();

    }


}
