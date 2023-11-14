package cn.hubindeveloper.rpc.client.Serializ;

import cn.hubindeveloper.rpc.client.Common.RpcRequest;
import cn.hubindeveloper.rpc.client.Common.RpcResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
public class KryoSerializer implements Serializer{
    /**
     * 由于 Kryo 不是线程安全的。每个线程都应该有自己的 Kryo，Input 和 Output 实例。
     * 所以，使用 ThreadLocal 存放 Kryo 对象
     * ThreadLocal<T>其实是与线程绑定的一个变量。ThreadLocal和 Synchronized都用于解决多线程并发访问。
     * Synchronized用于线程间的数据共享，而ThreadLocal则用于线程间的数据隔离。
     * Synchronized是利用锁的机制，使变量或代码块在某一时该只能被一个线程访问。
     * 而ThreadLocal为每一个线程都提供了变量的副本使得每个线程在某一时间访问到的并不是同一个对象，这样就隔离了多个线程对数据的数据共享。
     * withInitial()方法统一初始化所有线程的ThreadLocal的值
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();
            return output.toBytes();
        }catch (Exception e){
            log.error("序列化失败！");
            throw new RuntimeException();
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try{
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();
            return clazz.cast(o);
        }catch (Exception e){
            log.error("反序列化失败！");
            throw new RuntimeException();
        }
    }
}
