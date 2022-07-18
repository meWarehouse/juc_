package com.at.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @create 2022-07-18
 */
public class AtomicStampedReferenceAndAtomicMarkableReference {

    public static void main(String[] args) {

//        m1();

        m2();

    }

    public static void m2(){

        /*
            原子更新带有标记位的引用类型对象 AtomicMarkableReference

                解决是否修改过
                类似一次性筷子
         */

        AtomicMarkableReference<Integer> reference = new AtomicMarkableReference<Integer>(0,false);


        new Thread(() -> {

            boolean marked = reference.isMarked();

            System.out.println(Thread.currentThread().getName() + " 默认表标识：" + marked);

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            boolean isSuccess = reference.compareAndSet(0, 100, marked, !marked);

            System.out.println(Thread.currentThread().getName() + " 是否修改成功 " + isSuccess + " value = " + reference.getReference().intValue() + " ,mark = " + reference.isMarked() );


        },"A").start();

        new Thread(() -> {

            boolean marked = reference.isMarked();

            System.out.println(Thread.currentThread().getName() + " 默认表标识：" + marked);


            boolean isSuccess = reference.compareAndSet(0, 100, marked, !marked);

            System.out.println(Thread.currentThread().getName() + " 第一次修改是否成功 " + isSuccess + " value = " + reference.getReference().intValue() + " ,mark = " + reference.isMarked() );



        },"B").start();


    }


    public static void m1(){

        /*
            原子更新带有stamp的引用类型对象  AtomicStampedReference

                解决修改多次



         */

        AtomicStampedReference<Integer> reference = new AtomicStampedReference<Integer>(0,0);

        new Thread(() -> {

            int intValue = reference.getReference().intValue();
            int stamp = reference.getStamp();

            System.out.println(Thread.currentThread().getName() + " 线程操作前， value = " + intValue + ", stamp = " + stamp);

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            boolean isSuccess = reference.compareAndSet(intValue, 100, stamp, stamp + 1);

            System.out.println(Thread.currentThread().getName() + " 线程操作是否成功 " + isSuccess +" ， value = " + reference.getReference().intValue() + ", stamp = " + reference.getStamp());

        },"A").start();

        try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }


        new Thread(() -> {

            int intValue = reference.getReference().intValue();
            int stamp = reference.getStamp();

            System.out.println(Thread.currentThread().getName() + " 线程操作前， value = " + intValue + ", stamp = " + stamp);

            boolean isSuccess = reference.compareAndSet(intValue, 100, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + " 线程第一操作 是否成功 "+ isSuccess +"， value = " + reference.getReference().intValue() + ", stamp = " + reference.getStamp());


            boolean bool = reference.compareAndSet(reference.getReference().intValue(), 0, reference.getStamp(), reference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "  线程第一操作 是否成功 " + bool + "， value = " + reference.getReference().intValue() + ", stamp = " + reference.getStamp());

        },"B").start();


    }


}
