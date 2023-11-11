package cn.hubindeveloper.rpc.server.Socket;

import cn.hubindeveloper.rpc.client.Common.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
public class HelloServer {
    private static final Logger logger = LoggerFactory.getLogger(HelloServer.class);
    public void start(int port){
        try{
            ServerSocket server = new ServerSocket(port);
            Socket socket;
            while((socket = server.accept()) != null){
                logger.info("client connected!");
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                Message message = (Message) inputStream.readObject();
                logger.info("server receive message: {}", message);
                message.setContent("new content");
                outputStream.writeObject(message);
                outputStream.flush();
            }

        }catch (IOException  | ClassNotFoundException e){
            logger.error("occur exception:" + e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloServer.class, args);
        HelloServer helloServer = new HelloServer();
        helloServer.start(6666);
    }
}
