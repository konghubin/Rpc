package cn.hubindeveloper.rpc.client.Netty;

import cn.hubindeveloper.rpc.client.Coder.NettyKryoDecoder;
import cn.hubindeveloper.rpc.client.Coder.NettyKryoEncoder;
import cn.hubindeveloper.rpc.client.Common.RpcRequest;
import cn.hubindeveloper.rpc.client.Common.RpcResponse;
import cn.hubindeveloper.rpc.client.Serializ.KryoSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@AllArgsConstructor
public class NettyClient {
    private final String host;
    private final int port;

    /**
     * Bootstrap类是Netty提供的一个便利的工厂类。
     * 可以通过它来完成Netty的客户端或服务端的Netty组件的组装，以及Netty程序的初始化和启动执行。
     * 在Netty中，每一个NioSocketChannel通道所封装的都是Java NIO通道。
     */
    private static final Bootstrap b;

    static {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        b = new Bootstrap();
        KryoSerializer kryoSerializer = new KryoSerializer();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch){
                        ch.pipeline().addLast(new NettyKryoDecoder(kryoSerializer, RpcResponse.class));
                        ch.pipeline().addLast(new NettyKryoEncoder(kryoSerializer, RpcRequest.class));
                        ch.pipeline().addLast(new NettyClientHandler());
                    }
                });
    }

    public RpcResponse sendMessage(RpcRequest rpcRequest){
        try{
            ChannelFuture f = b.connect(host, port).sync();
            log.info("client connect  {}", host + ":" + port);
            Channel futureChannel =  f.channel();
            log.info("send message");
            if(futureChannel != null){
                futureChannel.writeAndFlush(rpcRequest).addListener(future -> {
                    if(future.isSuccess()){
                        log.info("client send message: {}", rpcRequest.toString());
                    }else{
                        log.error("Send Failed:", future.cause());
                    }
                });
                futureChannel.closeFuture().sync();
                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                return futureChannel.attr(key).get();
            }
        }catch (Exception e){
            log.error("occur exception when connect server:", e);
        }
        return null;
    }

    public static void main(String[] args) {
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName("interface")
                .methodName("hello").build();
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8889);
        for (int i = 0; i < 3; i++) {
            nettyClient.sendMessage(rpcRequest);
        }
        RpcResponse rpcResponse = nettyClient.sendMessage(rpcRequest);
        System.out.println(rpcResponse.toString());
    }
}
