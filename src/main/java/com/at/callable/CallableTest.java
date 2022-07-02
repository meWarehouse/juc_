package com.at.callable;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @create 2022-07-02
 */
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        /*
            Callable 是 Runnable 的 增强版,搭配 FutureTask 使用

         */

        FutureTask<Integer> futureTask = new FutureTask<>(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return 0;
                    }
                });

        new Thread(futureTask).start();

        System.out.println(futureTask.get());

        System.out.println(futureTask.get(1, TimeUnit.SECONDS));

    }

}
