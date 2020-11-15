package my.cat.startup;

import my.cat.component.Server;
import my.cat.component.StandardEngine;
import my.cat.component.StandardServer;
import my.cat.component.StandardService;
import my.cat.net.Connector;

public class BootStrap {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println("启动服务器...");
//        Server server = new StandardServer();
//        server.addService(new StandardService("first service"));
//        server.findService("first service").setContainer(new StandardEngine());
//        server.init();
//        server.start();
//        server.stop();
//        server.destory();

        Connector connector = new Connector(9999);
        connector.init();
        connector.start();
//        connector.stop();
//        connector.destory();
        Thread.sleep(Long.MAX_VALUE);
    }
}
