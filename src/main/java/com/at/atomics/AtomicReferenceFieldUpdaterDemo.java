package com.at.atomics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @create 2022-07-20
 */
public class AtomicReferenceFieldUpdaterDemo {

    //多线程并发调用一个类的初始化方法，如果未被初始化过，将执行初始化工作，要求只能初始化一次

    public static void main(String[] args) {

        Var var = new Var();

        for (int i = 0; i < 5; i++) {

            new Thread(() -> {
                var.init(var);
            },String.valueOf(i)).start();

        }


    }

}



class Var{

    private volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<Var,Boolean> atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Var.class,Boolean.class,"isInit");

    public void init(Var var){

        if(atomicReferenceFieldUpdater.compareAndSet(var,Boolean.FALSE,Boolean.TRUE)){

            System.out.println(Thread.currentThread().getName() + " -> start init...");

            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println(Thread.currentThread().getName() + " -> start finish。。。");


        }else {

            System.out.println(Thread.currentThread().getName() + " -> 无法获取到资源" );

        }

    }



}