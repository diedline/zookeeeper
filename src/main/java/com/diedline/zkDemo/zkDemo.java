package com.diedline.zkDemo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class zkDemo {
    private String connect = "diedline:2181";
    private int timeOut = 2000;
    private ZooKeeper zooKeeper = null;

    //获取zookeeper的客户端
    @Before
    public void getClient() throws IOException {
        //接收到zookeeper发来的消息后做出的处理业务逻辑
        zooKeeper = new ZooKeeper(connect, timeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "---" + watchedEvent.getPath());
            }
        });
    }

    //创建一个节点
    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        String path = zooKeeper.create("/test", "i love pan".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(path);
    }

    /**
     * 查看节点是否存在
     */
    @Test
    public void testExist() throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists("/test", false);
        System.out.println(stat.toString());
    }


    /**
     * 查看子节点
     */
    @Test
    public void testList() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/test", true);
        for (String child:children){
            System.out.println(child);
        }
    }
}
