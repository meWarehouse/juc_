package com.at.threadlocal;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @create 2021-10-17
 */
public class ReferenceDemo {


    /*

当内存不足，JVM开始垃圾回收，对于强引用的对象，就算是出现了OOM也不会对该对象进行回收，死都不收

软引用是一种相对强引用弱化了一些的引用，需要用java.lang.ref.SoftReference类来实现， 当系统内存充足时它 不会被回收，不足时它 会 被回收

弱引用需要用java.lang.ref.WeakReference类来实现，它比软引用的生存期更短，只要垃圾回收机制一运行，不管JVM的内存空间是否足够，都会回收该对象占用的内存

虚引用需要java.lang.ref.PhantomReference类来实现，如果一个对象仅持有虚引用，那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收


     */

    public static void main(String[] args)
    {
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(new MyObject(),referenceQueue);
        System.out.println(phantomReference.get());

        List<byte[]> list = new ArrayList<>();

        new Thread(() -> {
            while (true)
            {
                list.add(new byte[1 * 1024 * 1024]);
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(phantomReference.get());
            }
        },"t1").start();

        new Thread(() -> {
            while(true)
            {
                Reference<? extends MyObject> poll = referenceQueue.poll();
                if (poll != null) {
                    System.out.println("------有虚对象进入了队列");
                }
            }
        },"t2").start();

        //暂停几秒钟线程
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }

    }

    public static void weakReference()
    {
        WeakReference<MyObject> weakReference = new WeakReference(new MyObject());
        System.out.println("gc before: "+weakReference.get());

        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after: "+weakReference.get());
    }

    public static void softReference()
    {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());//软引用

        /*内存够用
        System.out.println("gc before内存够用: "+softReference);

        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after内存够用: "+softReference);*/

        //设置参数-Xms10m -Xmx10m
        System.out.println("gc before: "+softReference);

        try
        {
            byte[] bytes = new byte[9 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("-----gc after内存不够: "+softReference.get());
        }
    }

    public static void strongReference()
    {
        MyObject myObject = new MyObject();//默认，强引用,死了都不放手
        System.out.println("gc before: "+myObject);

        myObject = null;
        System.gc();//手动挡的方式开启Gc回收。
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("gc after: "+myObject);
    }
}

class MyObject
{
    //一般这个方法工作中不用，此处为了讲解gc，演示
    @Override
    protected void finalize() throws Throwable
    {
        System.out.println("------------- gc ,finalize() invoked");
    }
}