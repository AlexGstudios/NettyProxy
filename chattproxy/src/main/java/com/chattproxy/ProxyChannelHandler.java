package com.chattproxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ProxyChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final Channel channel;

    public ProxyChannelHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext chx, ByteBuf byteBuf) throws Exception {
        ByteBuf clone = Unpooled.copiedBuffer(byteBuf);
        channel.writeAndFlush(clone);
    }

}
