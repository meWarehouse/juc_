package com.at.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @create 2022-07-17
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomic = new AtomicInteger(1);

        System.out.println(atomic.compareAndSet(1,2) + " -> " + atomic.get());

        System.out.println(atomic.compareAndSet(1,3) + " -> " + atomic.get());

    }

}
