package com.at.lockupgrade;

import org.openjdk.jol.info.ClassLayout;

/**
 * @create 2022-07-24
 */
public class LockUpgrade {

    public static void main(String[] args) {


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
