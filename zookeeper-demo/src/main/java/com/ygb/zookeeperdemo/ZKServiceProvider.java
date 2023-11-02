package com.ygb.zookeeperdemo;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * TODO
 *
 * @Author ygb
 * @Version 1.0
 * @Date 2022/10/25 19:18
 */
public class ZKServiceProvider implements Watcher {


    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("/create", 20, new ZKServiceProvider());
        zooKeeper.create("/c1","ygb".getBytes(),null,CreateMode.CONTAINER);
    }
    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
