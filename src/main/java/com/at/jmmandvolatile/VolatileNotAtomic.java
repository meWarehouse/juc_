package com.at.jmmandvolatile;

import java.util.concurrent.TimeUnit;

/**
 * @create 2022-07-17
 */
public class VolatileNotAtomic {










    private volatile int number = 0;

    public void add0(){
        number++;  //不具备原子性，该操作是先读取值，然后写回一个新值，相当于原来的值加上1，分3步完成
    }
/*
  public void add0();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=3, locals=1, args_size=1
         0: aload_0
         1: dup
         2: getfield      #2                  // Field number:I
         5: iconst_1
         6: iadd
         7: putfield      #2                  // Field number:I
        10: return
      LineNumberTable:
        line 13: 0
        line 14: 10
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
            0      11     0  this   Lcom/at/jmmandvolatile/VolatileNotAtomic;

 */
















    public synchronized void add1(){
        number = number + 1;
    }









    public static void main(String[] args) {

        VolatileNotAtomic volatileNotAtomic = new VolatileNotAtomic();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    volatileNotAtomic.add1();
//                    volatileNotAtomic.add0();
                }
            },String.valueOf(i)).start();
        }


//        while (Thread.activeCount() < 3){
//            System.out.println(Thread.currentThread().getName() + " " + volatileNotAtomic.number);
//        }

        try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + " " + volatileNotAtomic.number);


    }

}
