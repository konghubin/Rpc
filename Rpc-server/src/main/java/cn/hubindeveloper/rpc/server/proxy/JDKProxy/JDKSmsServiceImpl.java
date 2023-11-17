package cn.hubindeveloper.rpc.server.proxy.JDKProxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDKSmsServiceImpl implements JDKSmsService {
    @Override
    public String send(String message) {
        log.info("message: {}", message);
        return message;
    }
}
