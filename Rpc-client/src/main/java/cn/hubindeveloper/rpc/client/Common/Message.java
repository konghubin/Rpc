package cn.hubindeveloper.rpc.client.Common;

import java.io.Serializable;

public class Message implements Serializable {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message(String content) {
        this.content = content;
    }
}
