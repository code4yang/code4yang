package com.yang.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by yang on 2018/07/15-0015.
 */
public class JedisTest {
    private static Jedis jedis;

    static {
//        使用静态代码块初始化 jedis 连接对象，在类加载时自动执行
        jedis = new Jedis("localhost", 6379);
    }

    public static void main(String[] args) {
        stringTest();
    }


    private static void stringTest() {
//        添加一个键为name，值为yang的string类型数据
        String value = jedis.set("name", "yang");
//        获取键为name的string类型值
        String getValue = jedis.get("name");

        System.out.println("set return: " + value);
        System.out.println("get return: " + getValue);

//        设置键值，并获取旧值
        value = jedis.getSet("name", "code4yang");
        getValue = jedis.get("name");
        System.out.println("getSet return: " + value);
        System.out.println("get return: " + getValue);

        jedis.set("count", "1");

//        设置键为count的值自增长1，和count++类似，但为atomic操作，不会出现并发问题
        Long count = jedis.incr("count");
        System.out.println("incr return: " + count);

//        设置键为count的值自减1,和count--类似，为atomic操作，不会出现并发问题
        count = jedis.decr("count");
        System.out.println("decr return: " + count);

//        设置键为exp的值为exp value，且过期时间为5秒
        jedis.setex("exp", 5, "exp value");

        String exp = jedis.get("exp");
        System.out.println("get exp after setex return: " + exp);

        Long expTtl = jedis.ttl("exp");
        System.out.println("ttl return: " + expTtl);

        try {
            // 强制休眠5s
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        exp = jedis.get("exp");
        System.out.println("get exp after sleep 5s return: " + exp);

        expTtl = jedis.ttl("exp");
        System.out.println("ttl after 5s return: " + expTtl);

        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        expTtl = jedis.ttl("exp");
        System.out.println("ttl after 10s return: " + expTtl);

    }

    private static void listTest(){

    }
}
