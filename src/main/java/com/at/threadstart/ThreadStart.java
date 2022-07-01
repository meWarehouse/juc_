package com.at.threadstart;

/**
 * @create 2022-06-30
 */
public class ThreadStart {

    public static void main(String[] args) {

        System.out.println("main thread: " + Thread.currentThread().getId());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable thread: " + Thread.currentThread().getId());
                System.out.println("Runnable");
            }
        };

        new Thread(runnable).start();

        /*
            java.lang.Thread.start
                public synchronized void start() {
                    if (threadStatus != 0)
                        throw new IllegalThreadStateException();

                    group.add(this);

                    boolean started = false;
                    try {
                        start0(); // -> private native void start0(); 调用本地方法
                        started = true;
                    } finally {
                        try {
                            if (!started) {
                                group.threadStartFailed(this);
                            }
                        } catch (Throwable ignore) {
                        }
                    }
                }
         */

    }

}
