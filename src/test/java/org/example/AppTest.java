package org.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void echoServer() throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(InetAddress.getByName("localhost"), 9001), 2);
        while (true) {
            Thread.sleep(Long.MAX_VALUE);
            Socket socket = serverSocket.accept();
            new Thread(new EchoRunnable(socket)).start();
        }
    }

    private class ReadRunnable implements Runnable {
        private Socket socket;

        public ReadRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.err.println(line);
                }
                System.err.println("111111111");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class WriteRunnable implements Runnable {
        private Socket socket;

        public WriteRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
                printWriter.println("欢迎你: " + socket.getInetAddress().getHostAddress());
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("C:\\Users\\huixiang\\Desktop\\a.txt")));
                String line;
                while ((line = bufferedReader.readLine()) != null){
                    Thread.sleep(3000);
                    printWriter.println("server: " + line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private class EchoRunnable implements Runnable {
        private Socket socket;

        public EchoRunnable(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                new Thread(new WriteRunnable(socket),"write----------").start();
                new Thread(new ReadRunnable(socket),"read----------").start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testNetworkInterfacae() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//        while (networkInterfaces.hasMoreElements()){
//            NetworkInterface networkInterface = networkInterfaces.nextElement();
//            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
//            System.out.println(networkInterface.getName());
//            while (inetAddresses.hasMoreElements()){
//                InetAddress inetAddress = inetAddresses.nextElement();
//                System.out.println(inetAddress.getHostAddress());
//            }
//        }
        InetAddress byName = InetAddress.getByName("www.baidu.com");
        System.out.println(byName.getLocalHost());
        System.out.println(byName.getHostName());
        System.out.println(byName.getHostAddress());
        System.out.println(byName.getCanonicalHostName());
        System.out.println(new String(byName.getAddress()));
    }

    @Test
    public void testServerSocket() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(9001, 1024);
        Map<String, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try (Socket socket = serverSocket.accept()) {
                        System.out.println(Thread.currentThread().getName() + " " + socket.getInetAddress() + " " + socket.getPort());
                        map.computeIfAbsent(Thread.currentThread().getName(), key -> 0);
                        map.computeIfPresent(Thread.currentThread().getName(), (key, oldValue) -> oldValue + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, "accpet-" + i).start();
        }
        long l = System.currentTimeMillis();
        while (l + 10000000L > System.currentTimeMillis()) {
            Thread.sleep(500);
        }
        Thread.sleep(5000);
        System.out.println(map);
    }

    @Test
    public void connectServer() {
        int i = 10000;
        while (i > 0) {
            Socket socket = null;
            try {
                socket = new Socket("localhost", 9001);
                System.out.println(socket.getLocalPort());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            i--;
        }
    }
}
