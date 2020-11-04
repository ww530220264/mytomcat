package my.cat.startup;

import my.cat.component.Server;
import my.cat.component.StandardEngine;
import my.cat.component.StandardServer;
import my.cat.component.StandardService;

public class BootStrap {
    public static void main(String[] args) {
        System.out.println("启动服务器...");
        Server server = new StandardServer();
        server.addService(new StandardService("first service"));
        server.findService("first service").setContainer(new StandardEngine());
        server.init();
        server.start();
        server.stop();
        server.destory();
    }
}
