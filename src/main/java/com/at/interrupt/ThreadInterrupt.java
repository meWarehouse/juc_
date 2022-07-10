package com.at.interrupt;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @create 2022-07-10
 */
public class ThreadInterrupt {

    private static volatile boolean isStop = false;

    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " --- " + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + " --- " + Thread.interrupted());


        System.out.println("running Main 111...");
        Thread.currentThread().interrupt(); // 将标志位设置为 true
        System.out.println("running Main 222...");


        // Thread.interrupted() 返回当前线程的中断状态  将当前线程的中断状态设为false

        System.out.println(Thread.currentThread().getName() + " --- " + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + " --- " + Thread.interrupted());

    }


    public static void m5(String[] args) {

        Thread A = new Thread(() -> {

            while (true){

                if(Thread.currentThread().isInterrupted()){

                    System.out.println(Thread.currentThread().getName() + " 线程被中断....");
                    break;

                }

//                java.lang.InterruptedException: sleep interrupted
//                at java.lang.Thread.sleep(Native Method)
//                at java.lang.Thread.sleep(Thread.java:340)
//                at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
//                at com.at.interrupt.ThreadInterrupt.lambda$main$0(ThreadInterrupt.java:30)
//                at java.lang.Thread.run(Thread.java:748)
                /**
                 * sleep方法抛出InterruptedException后，中断标识也被清空置为false，
                 * 在catch没有通过调用th.interrupt（）方法再次将中断标识置为true，
                 * 这就导致死限环了
                 *
                 * If this thread is blocked in an invocation of the wait(), wait(long), or wait(long, int)
                 * methods of the Object class, or of the join(), join(long), join(long, int),
                 * sleep(long), or sleep(long, int), methods of this class,
                 * then its interrupt status will be cleared and it will receive an InterruptedException.
                 *
                 */
                try {
                    TimeUnit.MILLISECONDS.sleep(3);
                } catch (InterruptedException e) {


                    Thread.currentThread().interrupt(); // 解决办法

                    e.printStackTrace();
                }


                System.out.println(Thread.currentThread().getName() + " running....");

            }

        }, "A");


        A.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            A.interrupt();
        }).start();



    }



    /**
     * 中断为true后，并不是立刻stop程序
     */
    public static void m4(String[] args) {

        Thread A = new Thread(() -> {

            for (int i = 0; i < 500; i++) {
                System.out.println(Thread.currentThread().getName() + " -> " + i);
            }

            System.out.println("A.interrupt()调用之后 -- 0： " + Thread.currentThread().isInterrupted());

        }, "A");

        A.start();

        System.out.println("A.interrupt() 调用之前，A 线程的中断默认值：" + A.isInterrupted());


        try { TimeUnit.MILLISECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        A.interrupt();
        System.out.println("A.interrupt() 调用之后，A线程的中断标识：" + A.isInterrupted());


        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }


        //非活动状态,A线程不在执行中，已经结束执行了。
        System.out.println("A.interrupt() 调用之后" + A.isInterrupted());

    }


    /**
     * 通过Thread类自带的中断api方法实现 isInterrupted()  interrupt()
     *      中断了 是不是线程就立即stop ?????
     */
    public static void m3(String[] args) {

        Thread A = new Thread(() -> {

            while (true){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println(String.format("... %s 线程被中断",Thread.currentThread().getName()));
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 线程 running...");
            }

        }, "A");

        A.start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {

            A.interrupt();
            System.out.println("线程 A 已中断");

        },"B").start();

        System.out.println("main thread running.....");


    }



    /**
     * AtomicBoolean 原子引用
     */
    public static void m2(String[] args) {

        new Thread(() -> {

            while (true){
                if(atomicBoolean.get()){
                    System.out.println(String.format("... %s 线程被中断",Thread.currentThread().getName()));
                    break;
                }
                System.out.println(Thread.currentThread().getName() + " 线程 running...");
            }

        },"A").start();

        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
            System.out.println("线程 " + Thread.currentThread().getName() + " 将 线程 A 中断");
        },"B").start();

        System.out.println("main thread running.....");

    }

    /**
     * volatile 可见性
     */
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
