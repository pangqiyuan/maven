import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class TestRedis {
    @Test
    public void test(){
        Jedis jedis = new Jedis("192.168.31.31",6379);
//        List<String> mget = jedis.mget("name","age");
//        for (Iterator<String> iterator = mget.iterator(); iterator.hasNext(); ) {
//            String next =  iterator.next();
//            System.out.println(next);
//
//        }
        HashMap<String, String> map = new HashMap<>();
        map.put("name","老婆");
        map.put("sex","man");
        jedis.hmset("user",map);
    }

    @Test
    public void test1(){
        HashSet<HostAndPort> ports = new HashSet<>();
        ports.add(new HostAndPort("192.168.31.31",7001));
        ports.add(new HostAndPort("192.168.31.31",7002));
        ports.add(new HostAndPort("192.168.31.31",7003));
        ports.add(new HostAndPort("192.168.31.31",7004));
        ports.add(new HostAndPort("192.168.31.31",7005));
        ports.add(new HostAndPort("192.168.31.31",7006));

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(20);
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxWaitMillis(-1);
        poolConfig.setTestOnBorrow(true);

        JedisCluster cluster = new JedisCluster(ports,6000,100,poolConfig);
        System.out.println(cluster.set("sex","man"));
        System.out.println(cluster.get("sex "));
    }
}
