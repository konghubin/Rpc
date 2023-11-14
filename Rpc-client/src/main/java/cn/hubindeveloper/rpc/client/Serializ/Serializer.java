package cn.hubindeveloper.rpc.client.Serializ;

public interface Serializer {
    // 序列化
    byte[] serialize(Object obj);

    //反序列化
    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
