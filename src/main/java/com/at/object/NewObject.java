package com.at.object;

import org.openjdk.jol.vm.VM;

/**
 * @create 2022-07-23
 */
public class NewObject {

    /*
        <!--
            官网：http://openjdk.java.net/projects/code-tools/jol/
            定位：分析对象在JVM的大小和分布
        -->
        <dependency>
            <groupId>org.openjdk.jol</groupId>
            <artifactId>jol-core</artifactId>
            <version>0.9</version>
        </dependency>
     */

    public static void m1(String[] args) {

        //VM的细节详细情况
        System.out.println(VM.current().details());


        //所有的对象分配的字节都是8的整数倍。
        System.out.println(VM.current().objectAlignment());

        /*
# Running 64-bit HotSpot VM.
# Using compressed oop with 3-bit shift.
# Using compressed klass with 3-bit shift.
# Objects are 8 bytes aligned.
# Field sizes by type: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]
# Array element sizes: 4, 1, 1, 2, 2, 4, 4, 8, 8 [bytes]

8
         */

    }

}
