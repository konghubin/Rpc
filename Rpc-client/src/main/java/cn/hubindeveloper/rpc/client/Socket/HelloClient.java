package cn.hubindeveloper.rpc.client.Socket;

import cn.hubindeveloper.rpc.client.Common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
@SpringBootApplication
public class HelloClient {
    private static final Logger logger = LoggerFactory.getLogger(HelloClient.class);

    public Object send(Message message, String host, int port){
        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(message);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            return inputStream.readObject();

        }catch (IOException | ClassNotFoundException e){
            logger.error("occur exception:" + e);
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClient.class, args);
        HelloClient client = new HelloClient();
        Message message = (Message) client.send(new Message("content from client"), "127.0.0.1", 6666);
        logger.info("client receive message: {}", message.getContent());
    }
}
