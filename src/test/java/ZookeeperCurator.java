import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicValue;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import sun.security.provider.certpath.DistributionPointFetcher;

import java.util.AbstractMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ZookeeperCurator {
    public static final String connectString="192.168.31.31:2181";
//    public static final int sessionOutTiome=5000;
    static ReentrantLock  reentrantLock=new ReentrantLock();
    static int count =10;
    public static void genarNo(){
        try {
            reentrantLock.lock();
            count--;
            System.out.println(count);
        }finally {
            reentrantLock.unlock();
        }

    }
    public static void main(String[] args) throws Exception {

        //重试策略
        RetryPolicy retryPolicy  = new ExponentialBackoffRetry(1000,10);
        final CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(connectString).retryPolicy(retryPolicy).build();
        curatorFramework.start();
           DistributedDoubleBarrier distributedDoubleBarrier =new DistributedDoubleBarrier(curatorFramework,"/super",5);
           Thread.sleep(1000*(new Random()).nextInt(3));
        System.out.println(Thread.currentThread().getName()+"已经准备");
        distributedDoubleBarrier.enter();
        Thread.sleep(1000*(new Random()).nextInt(3));
        distributedDoubleBarrier.leave();
        DistributedBarrier barrier = new DistributedBarrier(curatorFramework, "/super");
        barrier.setBarrier();
        barrier.waitOnBarrier();
        barrier.removeBarrier();
//       curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/master/lock");
//        DistributedAtomicInteger distributedAtomicInteger=new DistributedAtomicInteger(curatorFramework,"/super",new RetryNTimes(3,1000));
//
//        distributedAtomicInteger.forceSet(0);
//        AtomicValue<Integer> value=distributedAtomicInteger.get();
//        System.out.println(value.succeeded());
//        System.out.println(value.postValue());  //最新值
//        System.out.println(value.preValue());     //原始值
//        curatorFramework.setData().forPath("/master","keyi".getBytes());
//        String s =  new String(curatorFramework.getData().forPath("/master"));
//        System.out.println(s);
//        ExecutorService pool = Executors.newCachedThreadPool();
//        curatorFramework.create().inBackground(new BackgroundCallback() {
//            @Override
//            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
//                System.out.println(curatorEvent.getPath());
//                System.out.println(curatorEvent.getType());
//            }
//        },pool).forPath("/node1");
//        Thread.sleep(Integer.MAX_VALUE);
//
//        final NodeCache cache = new NodeCache(curatorFramework, "/master/lock");
//        cache.start(true);
//        cache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                if(cache.getCurrentData().getPath().contains("/master/lock")){
//                    System.out.println("时间间隔");
//                }
//            }
//        });
//        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, "/super", true);
//        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
//        cache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                switch (pathChildrenCacheEvent.getType()){
//                    case CHILD_ADDED:
//                        if(pathChildrenCacheEvent.getData().getPath().equals("/bj")){
//                            System.out.println("bj");
//                        }else if (pathChildrenCacheEvent.getData().getPath().equals("/sz")){
//                            System.out.println("/sz");
//                        }else if (pathChildrenCacheEvent.getData().getPath().equals("/sh")){
//                            System.out.println("/sh");
//                        }
//                        break;
//                    case CHILD_UPDATED:
//                        break;
//                    case CHILD_REMOVED:
//                         break;
//                }
//            }
//        });
//        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        for (int i=0;i<10;i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    final InterProcessMutex lock = new InterProcessMutex(curatorFramework,"/super");
//                    try {
//                        countDownLatch.await();
//                        lock.acquire();
//                        System.out.println(Thread.currentThread().getName()+"执行业务逻辑");
//                        Thread.sleep(1000);
////                        genarNo();
//
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally {
//                        try {
//                            lock.release();
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//
//                }
//            },"t"+ i).start();
//        }
//        Thread.sleep(1000);
//        countDownLatch.countDown();
    }
}
