package com.diedline.zkDemo;

import org.apache.zookeeper.*;

import java.io.IOException;

public class zkServer {
    private static String connect = "diedline:2181";
    private static int sessionTimeOut = 4000;
    private static ZooKeeper zooKeeper = null;
    private static String parentPath = "/servers";


    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //获取zookeeper客户端
        getClient();
        //启动注册
        registerServer(args[0]);
        //业务逻辑
        business(args[0]);
    }

    private static void business(String hostName) throws InterruptedException {
        System.out.println(hostName + "is working..");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void registerServer(String hostName) throws KeeperException, InterruptedException {
        //创建临时节点
        String path =
                zooKeeper.create(parentPath + "/server",
                        hostName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostName + "is online");

    }

    //获取zookeeper的客户端
    private static void getClient() throws IOException {
        zooKeeper = new ZooKeeper(connect, sessionTimeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "---" + watchedEvent.getPath());
            }
        });
    }
}
