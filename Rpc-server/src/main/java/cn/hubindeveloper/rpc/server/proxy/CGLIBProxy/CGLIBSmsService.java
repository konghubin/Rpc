package cn.hubindeveloper.rpc.server.proxy.CGLIBProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CGLIBSmsService {
    public String send(String message) {
        log.info("message: {}", message);
        return message;
    }
}
