package com.at.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create 2022-07-17
 */
public class AddAndAtomic {

    private volatile int i = 0;

    public void add0(){
        i++;
    }

    private AtomicInteger atomic = new AtomicInteger(0);

    public void addAtomic(){
        atomic.getAndIncrement();


        /*

        AtomicInteger 类主要利用 CAS (compare and swap) + volatile 和 native 方法来保证原子操作，
        从而避免 synchronized 的高开销，执行效率大为提升。

        CAS并发原语体现在JAVA语言中就是sun.misc.Unsafe类中的各个方法。
        调用UnSafe类中的CAS方法，JVM会帮我们实现出CAS汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。
        再次强调，由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，
        并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。


            public final int getAndIncrement() {
                return unsafe.getAndAddInt(this, valueOffset, 1);
            }

            public final int getAndAddInt(Object var1, long var2, int var4) {
                int var5;
                do {
                    var5 = this.getIntVolatile(var1, var2);
                } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));

                return var5;
            }

            public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);

         */

    }

    public static void main(String[] args) {

        AddAndAtomic t = new AddAndAtomic();


        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
//                    t.add0();
                    t.addAtomic();
                }
            },String.valueOf(i)).start();
        }


        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + " -> " + t.i);
        System.out.println(Thread.currentThread().getName() + " -> " + t.atomic.get());


    }

}
