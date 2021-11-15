package com.chattproxy;

public class Server {
    private int PORT;

    public Server(int ports) {
        this.PORT = ports;
    }

    public int getPort() {
        return this.PORT;
    }
}