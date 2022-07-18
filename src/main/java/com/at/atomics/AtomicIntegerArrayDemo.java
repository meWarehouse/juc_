package com.at.atomics;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @create 2022-07-18
 */
public class AtomicIntegerArrayDemo {


    /*

        AtomicIntegerArray
        AtomicLongArray
        AtomicReferenceArray


     */

    public static void main(String[] args) {


//        int[] ints = new int[5];
//        int[] ints = {1,2,3};

//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(5);
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3, 4, 5});


        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        System.out.println("=========================================");

        int tmp = 0;
        tmp = atomicIntegerArray.getAndSet(0,100);
        System.out.println("index = 0 -> value = " + atomicIntegerArray.get(0));

        atomicIntegerArray.getAndIncrement(0);
        atomicIntegerArray.getAndIncrement(0);
        tmp = atomicIntegerArray.getAndIncrement(0);

        System.out.println("index = 0 -> value = " + tmp + " -> v = " + atomicIntegerArray.getAndIncrement(0));


    }

}
