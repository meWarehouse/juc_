package com.at.threadlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @create 2022-07-23
 */
public class ThreadLocalDateUtils {

    /*

        SimpleDateFormat类内部有一个Calendar对象引用,它用来储存和这个SimpleDateFormat相关的日期信息,
        例如sdf.parse(dateStr),sdf.format(date) 诸如此类的方法参数传入的日期相关String,Date等等,
        都是交由Calendar引用来储存的.这样就会导致一个问题如果你的SimpleDateFormat是个static的,
        那么多个thread 之间就会共享这个SimpleDateFormat, 同时也是共享这个Calendar引用。

     */
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parse(String str) throws ParseException {
        return sdf.parse(str);
    }

    public static void main(String[] args) {

        /*
Exception in thread "0" Exception in thread "9" Exception in thread "2" Exception in thread "8" Exception in thread "7" Exception in thread "4" Exception in thread "1" java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2051)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
Exception in thread "3" java.lang.NumberFormatException: For input string: "11E211"
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:589)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2051)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2051)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1869)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: empty String
	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1842)
	at sun.misc.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
	at java.lang.Double.parseDouble(Double.java:538)
	at java.text.DigitList.getDouble(DigitList.java:169)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2056)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1869)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2051)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: empty String
	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:1842)
	at sun.misc.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
	at java.lang.Double.parseDouble(Double.java:538)
	at java.text.DigitList.getDouble(DigitList.java:169)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2056)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:1869)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: For input string: "1111E.21111"
	at sun.misc.FloatingDecimal.readJavaFormatString(FloatingDecimal.java:2043)
	at sun.misc.FloatingDecimal.parseDouble(FloatingDecimal.java:110)
	at java.lang.Double.parseDouble(Double.java:538)
	at java.text.DigitList.getDouble(DigitList.java:169)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2056)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
java.lang.NumberFormatException: For input string: ""
	at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)
	at java.lang.Long.parseLong(Long.java:601)
	at java.lang.Long.parseLong(Long.java:631)
	at java.text.DigitList.getLong(DigitList.java:195)
	at java.text.DecimalFormat.parse(DecimalFormat.java:2051)
	at java.text.SimpleDateFormat.subParse(SimpleDateFormat.java:2162)
	at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1514)
	at java.text.DateFormat.parse(DateFormat.java:364)
	at com.at.threadlocal.ThreadLocalDateUtils.parse(ThreadLocalDateUtils.java:15)
	at com.at.threadlocal.ThreadLocalDateUtils.lambda$main$0(ThreadLocalDateUtils.java:23)
	at java.lang.Thread.run(Thread.java:748)
Mon Jul 11 11:11:11 GMT+08:00 178958991
Mon Jul 11 11:11:11 GMT+08:00 199168991

         */
//        for (int i = 0; i < 10; i++) {
//            new Thread(() -> {
//                try {
//                    System.out.println(parse("2021-11-11 11:11:11"));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            },String.valueOf(i)).start();
//        }


        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {

                    // 解决方法1：不使用全局静态变量,为每个线程创建创建一个 SimpleDateFormat 对象

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    System.out.println(simpleDateFormat.parse("2021-11-11 11:11:11"));

                    simpleDateFormat = null; // help GC

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }


    }

}
