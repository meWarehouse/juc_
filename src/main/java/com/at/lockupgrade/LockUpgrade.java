package com.at.lockupgrade;

import org.openjdk.jol.info.ClassLayout;

/**
 * @create 2022-07-24
 */
public class LockUpgrade {


    public static void lightLock(String[] args) {

        // -XX:-UseBiasedLocking

        Object o = new Object();

        new Thread(() -> {

            synchronized (o){
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }

        },"A").start();

        /*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           d0 f0 36 30 (11010000 11110000 00110110 00110000) (808906960)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */

    }



    public static void biasedLock(String[] args) {


        /*

            查看偏向锁相关的默认参数 java -XX:+PrintFlagsInitial |grep BiasedLock*

            intx BiasedLockingBulkRebiasThreshold          = 20                                  {product}
            intx BiasedLockingBulkRevokeThreshold          = 40                                  {product}
            intx BiasedLockingDecayTime                    = 25000                               {product}
            intx BiasedLockingStartupDelay                 = 4000                                {product}
            bool TraceBiasedLocking                        = false                               {product}
            bool UseBiasedLocking                          = true                                {product}


            实际上偏向锁在JDK1.6之后是默认开启的，但是启动时间有延迟，所以需要添加参数-XX:BiasedLockingStartupDelay=0，让其在程序启动时立刻启动。

            开启偏向锁：
                -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0

            关闭偏向锁：关闭之后程序默认会直接进入------------------------------------------>>>>>>>>   轻量级锁状态
                -XX:-UseBiasedLocking

         */

        Object o = new Object();

        // -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
        new Thread(() -> {
            synchronized (o){
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }).start();

        /*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           e0 f4 5e 30 (11100000 11110100 01011110 00110000) (811529440)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */


        /*

-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0

java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           05 c8 62 2f (00000101 11001000 01100010 00101111) (795002885)
      4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */


    }





    public static void noLock(String[] args) {


        Object o = new Object();

        System.out.println(o.hashCode()); // 10进制hash
        System.out.println(Integer.toHexString(o.hashCode())); // 16进制hash
        System.out.println(Integer.toBinaryString(o.hashCode())); //2进制hash

        //00000001010010101110010110100101
        //       1010010101110010110100101

        System.out.println(ClassLayout.parseInstance(o).toPrintable());


        /*
21685669
14ae5a5
1010010101110010110100101
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 a5 e5 4a (00000001 10100101 11100101 01001010) (1256563969)
      4     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

         */


    }

}
