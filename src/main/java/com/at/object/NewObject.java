package com.at.object;

import org.openjdk.jol.info.ClassLayout;
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

    public static void main(String[] args) {

        Object o = new Object();

        System.out.println(o.hashCode());
        //21685669

        System.out.println(ClassLayout.parseInstance(o).toPrintable());




        /*
java.lang.Object object internals:
 OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
      0     4        (object header)                           01 a5 e5 4a (00000001 10100101 11100101 01001010) (1256563969)
      4     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
     12     4        (loss due to the next object alignment)
Instance size: 16 bytes
Space losses: 0 bytes internal + 4 bytes external = 4 bytes total


OFFSET	偏移量，也就是到这个字段位置所占用的byte数
SIZE	后面类型的字节大小
TYPE	是Class中定义的类型
DESCRIPTION	DESCRIPTION是类型的描述
VALUE	VALUE是TYPE在内存中的值


         */


    }


}
