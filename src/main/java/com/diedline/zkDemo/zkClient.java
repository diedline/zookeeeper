package com.diedline.zkDemo;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class zkClient {

    private static String connect = "diedline:2181";
    private static int timeOut = 2000;
    private static ZooKeeper zooKeeper = null;
    private static String parentPath = "/servers";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        //获取zooKeeper的客户端
        getClient();
        //获取服务器列表(主机名) 并监听
        getSevers();
        //业务逻辑
        business();
    }

    private static void business() throws InterruptedException {
        System.out.println("client is working !");
        Thread.sleep(Long.MAX_VALUE);
    }

    private static void getSevers() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(parentPath, true);
        //用来装服务器主机名
        ArrayList<String> hosts = new ArrayList<String>();
        for (String child:children){
            byte[] data = zooKeeper.getData(parentPath + "/" + child, false, null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
    }


    private static void getClient() throws IOException {
        zooKeeper = new ZooKeeper(connect, timeOut, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "--" + watchedEvent.getPath());
                //重新获取客户端的列表
                try {
                    getSevers();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
