package com.at.completablefuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * * 案例说明：电商比价需求
 * * 1 同一款产品，同时搜索出同款产品在各大电商的售价;
 * * 2 同一款产品，同时搜索出本产品在某一个电商平台下，各个入驻门店的售价是多少
 * *
 * * 出来结果希望是同款产品的在不同地方的价格清单列表，返回一个List<String>
 * * 《mysql》 in jd price is 88.05
 * * 《mysql》 in pdd price is 86.11
 * * 《mysql》 in taobao price is 90.43
 * *
 * * 3 要求深刻理解
 * *   3.1 函数式编程
 * *   3.2 链式编程
 * *   3.3 Stream流式计算
 */
public class CFDemo {

    static class NetMall {

        private String mallName;

        public NetMall(String mallName) {
            this.mallName = mallName;
        }

        public String getMallName() {
            return mallName;
        }

        public double calcPrice(String productName) {

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
        }


    }

    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("pdd"),
            new NetMall("taobao"),
            new NetMall("dangdangwang"),
            new NetMall("tmall")
    );

    //同步
    public static List<String> getPrice(List<NetMall> netMalls, String productName) {
        return netMalls
                .stream()
                .map(netMall -> String.format("%s in %s price is %.2f", productName, netMall.getMallName(), netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    //异步
    public static List<String> getPriceSync(List<NetMall> netMalls, String productName) {
        return netMalls
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format("%s in %s price is %.2f", productName, netMall.getMallName(), netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime - startTime) +" 毫秒");

        System.out.println();
        System.out.println("========================================================================");
        System.out.println();

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceSync(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----costTime: "+(endTime2 - startTime2) +" 毫秒");
    }


}


