package com.at.readwritelock;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @create 2022-07-24
 */
public class ReadWriteLock {

    public static void main(String[] args) {

        Resource resource = new Resource();

        for (int i = 0; i < 10; i++) {

            final String kv = String.valueOf(i);
            new Thread(() -> {
                resource.write(kv,kv);
            },String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            final String kv = String.valueOf(i);
            new Thread(() -> {
                resource.read(kv);
            },String.valueOf(i)).start();
        }



    }

}


class Resource{

    Map<String,String> map = new HashMap<>();

    Lock lock = new ReentrantLock();

    // 读读共享
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void write(String key,String value){

//        lock.lock();

        readWriteLock.writeLock().lock();

        try {

            System.out.println(Thread.currentThread().getName() + " -> 正在写入" );

            map.put(key,value);

            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + " -> 写入完成" );

        }finally {
//            lock.unlock();
            readWriteLock.writeLock().unlock();
        }

    }


    public void read(String key){

//        lock.lock();

        readWriteLock.readLock().lock();


        try {

            System.out.println(Thread.currentThread().getName() + " -> 正在读取" );

            String res = map.get(key);

            System.out.println(Thread.currentThread().getName() + " -> 读取完成 res = " + res );

        }finally {
//            lock.unlock();

            readWriteLock.readLock().unlock();

        }

    }


}
