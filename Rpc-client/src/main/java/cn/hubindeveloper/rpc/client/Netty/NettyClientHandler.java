package cn.hubindeveloper.rpc.client.Netty;

import cn.hubindeveloper.rpc.client.Common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse response = (RpcResponse) msg;
        log.info("client receive msg: {}", response.toString());
        AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
        ctx.channel().attr(key).set(response);
        ctx.channel().close();
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("client caught exception", cause);
        ctx.close();
    }
}
