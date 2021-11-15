package com.chattproxy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Proxy {

    private final int PORT;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workeGroup;

    List<Server> servers;

    public Proxy(int port) {
        this.PORT = port;
        this.bossGroup = new NioEventLoopGroup();
        this.workeGroup = new NioEventLoopGroup();
        this.servers = new ArrayList<>();
    }

    public void start() {

        try {
            getFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workeGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ProxyChannelInitializer(this)).bind(this.PORT).sync().channel().closeFuture()
                    .sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getFromFile() throws IOException {
        String file = "E://skalbaratjanster/chatt/proxy/Config.txt";

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();

        String[] ports = currentLine.split(", ");

        for (int i = 0; i < ports.length; i++) {
            servers.add(new Server(Integer.parseInt(ports[i])));
        }

        reader.close();
    }

    public EventLoopGroup getEventLoopGroup() {
        return workeGroup;
    }
}