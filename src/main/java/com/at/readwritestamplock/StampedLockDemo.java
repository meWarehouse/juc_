package com.at.readwritestamplock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @create 2022-07-24
 */
public class StampedLockDemo {

    static int number = 30;

    static StampedLock stampedLock = new StampedLock();

    public void write(){

        long writeLockStamp = stampedLock.writeLock();

        System.out.println(Thread.currentThread().getName() + " 开始准备写");

        try {
            number = number + 10;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stampedLock.unlockWrite(writeLockStamp);
        }

        System.out.println(Thread.currentThread().getName() + " 写完成");

    }


    public void read(){

        long readLockStamp = stampedLock.readLock();

        System.out.println(Thread.currentThread().getName() + " 开始读，持续4s....");

        for (int i = 0; i < 4; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 正在读数据....");
        }


        try {
            int res = number;
            System.out.println(Thread.currentThread().getName() + "\t" + " number = " + res);
            System.out.println("写线程没有修改值，因为 stampedLock.readLock()读的时候，不可以写，读写互斥");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            stampedLock.unlockRead(readLockStamp);
        }

    }

    public void tryOptimisticRead(){

        long tryOptimisticReadStamp = stampedLock.tryOptimisticRead();

        //先把数据取得一次
        int res = number;

        //间隔4秒钟，我们很乐观的认为没有其他线程修改过number值，愿望美好，实际情况靠判断。
        System.out.println("4秒前stampedLock.validate值(true无修改，false有修改)" + "\t" + stampedLock.validate(tryOptimisticReadStamp));

        for (int i = 0; i < 4; i++) {
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "\t 正在读取中......" + (i+1) +
                    "秒后stampedLock.validate值(true无修改，false有修改)" + "\t"
                    + stampedLock.validate(tryOptimisticReadStamp));
        }

        //乐观读失败后切换成普通读的模式
        if(!stampedLock.validate(tryOptimisticReadStamp)){

            System.out.println("有人动过--------存在写操作！");

            //有人动过了，需要从乐观读切换到普通读的模式。
            tryOptimisticReadStamp = stampedLock.readLock();

            try {
                System.out.println("从乐观读 升级为 悲观读并重新获取数据");
                //重新获取数据
                res = number;
                System.out.println("重新悲观读锁通过获取到的成员变量值result：" + res);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                stampedLock.unlockRead(tryOptimisticReadStamp);
            }

        }

        System.out.println(Thread.currentThread().getName() + "\t finally value: " + res);

    }


    public static void main(String[] args) {


        StampedLockDemo resource = new StampedLockDemo();

        // 1 悲观读,和ReentrantReadWriteLock一样
//        new Thread(() -> {
//            //悲观读
//            resource.read();
//        },"A").start();


        // =======================================================

        // 2 乐观读
//        new Thread(() -> {
//            //乐观读
//            resource.tryOptimisticRead();
//        },"B").start();
//
//        //6秒钟乐观读取resource.tryOptimisticRead()成功
//        try { TimeUnit.SECONDS.sleep(6); } catch (InterruptedException e) { e.printStackTrace(); }

        // =======================================================


        // 3 乐观读，失败，重新转为悲观读，重读数据一次
        new Thread(() -> {
            //乐观读
            resource.tryOptimisticRead();
        },"C").start();

        //2秒钟乐观读取resource.tryOptimisticRead()失败
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        // =======================================================


        new Thread(() -> {
            resource.write();
        },"write").start();



    }













}
