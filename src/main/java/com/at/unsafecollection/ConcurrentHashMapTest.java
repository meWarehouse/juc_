package com.at.unsafecollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @create 2022-07-02
 */
public class ConcurrentHashMapTest {

    public static void main(String[] args) {

        /*
java.util.ConcurrentModificationException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1437)
	at java.util.HashMap$EntryIterator.next(HashMap.java:1471)
	at java.util.HashMap$EntryIterator.next(HashMap.java:1469)
	at java.util.AbstractMap.toString(AbstractMap.java:554)
	at java.lang.String.valueOf(String.java:2994)
	at java.io.PrintStream.println(PrintStream.java:821)
	at com.at.unsafecollection.ConcurrentHashMapTest.lambda$main$0(ConcurrentHashMapTest.java:31)
	at java.lang.Thread.run(Thread.java:748)

         */
        Map<String,String> map = new HashMap<>();

        Map<String, String> syncMap = Collections.synchronizedMap(new HashMap<String, String>());

        Hashtable<String, String> hashtable = new Hashtable<>();

        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();


        for (int i = 0; i < 5000; i++) {

            new Thread(() -> {

//                map.put(UUID.randomUUID().toString(),UUID.randomUUID().toString().substring(1,6));
//                syncMap.put(UUID.randomUUID().toString(),UUID.randomUUID().toString().substring(1,6));
//                hashtable.put(UUID.randomUUID().toString(),UUID.randomUUID().toString().substring(1,6));
                concurrentHashMap.put(UUID.randomUUID().toString(),UUID.randomUUID().toString().substring(1,6));

                System.out.println(concurrentHashMap);

            },String.valueOf(i)).start();

        }



    }

}
