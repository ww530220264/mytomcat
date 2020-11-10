package org.example;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.junit.Test;

public class TestNetty {

//    public static void main(String[] args) throws Exception {
//        testServer();
//        testClient();
//    }
    @Test
    public void testClient() throws Exception {
        int port = 9001;

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                        socketChannel.pipeline()
                                .addLast(new LineBasedFrameDecoder(1024))
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    int count = 0;

                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.err.println("active");
                                        String msg = "hello, i want to get the current time." + System.getProperty("line.separator");
                                        byte[] bytes = msg.getBytes();
                                        for (int i = 0; i < 100; i++) {
                                            ctx.writeAndFlush(Unpooled.copiedBuffer(bytes));
                                        }
                                    }

                                    @Override
                                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                        ctx.flush();
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                                        System.err.println(++count);
                                        byte[] bytes = new byte[byteBuf.readableBytes()];
                                        byteBuf.readBytes(bytes);
                                        String inmsg = new String(bytes, "UTF-8");
                                        System.err.println(inmsg);
                                    }
                                });
                    }
                });
        ChannelFuture channelFuture = b.connect("localhost", port).sync();
        channelFuture.channel().closeFuture().sync();
    }
    @Test
    public void testServer() throws Exception {
        int port = 9001;
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LineBasedFrameDecoder(1024))
                                .addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                    int count = 0;

                                    @Override
                                    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                                        ctx.flush();
                                    }

                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
                                        System.err.println(++count);
                                        byte[] bytes = new byte[byteBuf.readableBytes()];
                                        byteBuf.readBytes(bytes);
                                        String msg = new String(bytes, "UTF-8");
                                        System.out.println(msg);
                                        String outmsg = "receive from " + ctx.channel().remoteAddress() + ": " + msg + System.getProperty("line.separator");
                                        ByteBuf outbuf = Unpooled.copiedBuffer(outmsg.getBytes());
                                        ctx.write(outbuf);
                                    }
                                });
                    }
                });

        ChannelFuture channelFuture = b.bind(port).sync();
        channelFuture.channel().closeFuture().sync();
    }
}
