package com.scut.seckill;

import com.alibaba.fastjson.JSON;
import com.scut.seckill.cache.RedisCacheHandle;
import com.scut.seckill.common.SecKillEnum;
import com.scut.seckill.constant.RedisCacheConst;
import com.scut.seckill.entity.Record;
import com.scut.seckill.entity.User;
import com.scut.seckill.mapper.SecKillMapper;
import com.scut.seckill.utils.SecKillUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import sun.rmi.runtime.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JUnitTest {

    @Autowired
    private SecKillMapper secKillMapper;

    @Autowired
    private RedisCacheHandle redisCacheHandle;

    private Set<String> set = new TreeSet<>();

    @Test
    public void test2() throws InterruptedException {
        Jedis jedis = redisCacheHandle.getJedis();
        boolean isBuy = jedis.sismember(RedisCacheConst.IPHONE_HAS_BOUGHT_SET, "1");
        System.out.println(isBuy);
    }

    @Test
    public void test3() throws InterruptedException, IOException {
        String[] array = {"1","2","3","4","5","6"};
        File file =new File("/usr/twc/gitProject/SecKillDesign/src/main/resources/jmeter/789.txt");
        FileWriter fileWritter = new FileWriter(file);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        Random random = new Random();
        for (int i = 8483; i <= 10000; i++) {
            StringBuffer buffer = new StringBuffer("");
            buffer.append(i+",");
            int max=6;
            int min=0;
            int s = random.nextInt(max)%(max-min+1) + min;
            buffer.append(array[s]);
                System.out.println(buffer.toString());
//            bufferWritter.write(buffer.toString());
//            bufferWritter.newLine();//??????
        }
    }

    @Test
    public void createUserSQL() throws IOException {
        List<String> list = new ArrayList<>();
        File file =new File("/usr/twc/gitProject/SecKillDesign/src/main/resources/sql/insert.sql");
        FileWriter fileWritter = new FileWriter(file);
        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
        for (int i=1001;i<=10000;i++){
            StringBuffer buffer = new StringBuffer("INSERT INTO `user` VALUES ('0', ");
            buffer.append("'"+"tom_"+i+"'"+",");
            buffer.append("'13855558888'"+",");
            buffer.append("current_date());");
            list.add(buffer.toString());
        }
        for (String string :list) {
            //INSERT INTO `user` VALUES ('0', 'tom_850','13855558888',current_date());
            System.out.println(string);
            bufferWritter.write(string);
            bufferWritter.newLine();//??????
        }
        bufferWritter.close();
    }

    @Test
    public void test4() throws InterruptedException {
        Jedis jedis = redisCacheHandle.getJedis();
        Transaction tx = jedis.multi();

        Response<Boolean> isBuy = tx.sismember("set", "2");
        System.out.println("isBuy------"+isBuy);

        Response<Long> decrResult = tx.hincrBy("product_1","stock",-1);
        System.out.println("decrResult------"+decrResult);

        tx.sadd("set","1");

        List<Object> resultList = tx.exec();
        System.out.println("-----------");
        System.out.println(Boolean.valueOf(resultList.get(0).toString()));
        System.out.println( Integer.parseInt(resultList.get(1).toString()));
        System.out.println( Integer.parseInt(resultList.get(2).toString()));
    }


    @Test
    public void redisSetTest() throws InterruptedException {
        Jedis jedis = redisCacheHandle.getJedis();

        long l = jedis.sadd("myset", "123");
        System.out.println(l);
        long l2 = jedis.sadd("myset", "123");
        System.out.println(l2);

        boolean flag = jedis.sismember("myset","123");
        System.out.println(flag);
    }

    @Test
    public void userTest() throws InterruptedException {
        User user = secKillMapper.getUserById(1);
        System.out.println(user);

        AtomicInteger atomicInteger = new AtomicInteger();
    }
}
