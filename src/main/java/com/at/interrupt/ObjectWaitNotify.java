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



    }


}


class Resource {

    private int shareResource = 0;


    // +1
    public synchronized void inc() throws InterruptedException {

        // 判断
        if (shareResource != 0) {
            // 等待
            wait();
        }

        // 干活
        shareResource += 1;

        System.out.println(Thread.currentThread().getName() + " -> " + shareResource);

        //通知
        notifyAll();

    }


    // -1
    public synchronized void desc() throws InterruptedException {

        //判断
        if (shareResource != 1) {
            // 等待
            wait();
        }

        // 干活
        shareResource -= 1;

        System.out.println(Thread.currentThread().getName() + " -> " + shareResource);


        //通知
        notifyAll();

    }


}
