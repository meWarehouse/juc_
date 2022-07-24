package com.at.readwritestamplock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @create 2022-07-24
 */
public class LockDowngrade {

    /*

        锁降级：遵循获取写锁→再获取读锁→再释放写锁的次序，写锁能够降级成为读锁。

        如果一个线程占有了写锁，在不释放写锁的情况下，它还能占有读锁，即写锁降级为读锁。

     */

    public static void main(String[] args) {

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();


/*  正常情况
        writeLock.lock();

        System.out.println("...正在写");
        System.out.println("...写完成");

        writeLock.unlock();


        readLock.lock();
        System.out.println("...开始读");
        System.out.println("...读完成");

        readLock.unlock();
*/

/*     写锁降级
        writeLock.lock();

        System.out.println("...正在写");

        //写后立即加读锁 写锁降级为读锁
        readLock.lock();
        System.out.println("...开始读");
        System.out.println("...读完成");
        readLock.unlock();


        System.out.println("...写完成");

        writeLock.unlock();
*/




/*      读锁不能升级
        readLock.lock();
        System.out.println("...开始读");

        // 读锁升级成写锁  -》 error
        writeLock.lock();
        System.out.println("...正在写");
        System.out.println("...写完成");
        writeLock.unlock();

        System.out.println("...读完成");

        readLock.unlock();

*/




    }

}
