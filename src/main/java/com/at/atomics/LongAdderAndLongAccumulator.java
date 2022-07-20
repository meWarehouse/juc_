package com.at.atomics;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @create 2022-07-20
 */
public class LongAdderAndLongAccumulator {

    LongAdder longAdder = new LongAdder();

    public void add() {
        longAdder.increment();
    }

    //LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y,000);
    LongAccumulator longAccumulator = new LongAccumulator(
            new LongBinaryOperator() {
                @Override
                public long applyAsLong(long left, long right) {
                    return left - right;
                }
            },
            000
    );


    public static void main(String[] args) {

        // LongAdder 只能做加减 不能做乘除
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.add(1L);
        longAdder.decrement();
        /*
            public void increment() {
                add(1L);
            }
         */


        System.out.println(longAdder.sum());
        System.out.println(longAdder.longValue());
        /*
            public long longValue() {
                return sum();
            }
         */


        //LongAccumulator 自定义
        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x * y, 2/*初始值*/);

        longAccumulator.accumulate(3);
        longAccumulator.accumulate(7);

        //2 * 3 * 7

        System.out.println(longAccumulator.longValue());
        /*
            public long longValue() {
                return get();
            }
         */


    }


}
