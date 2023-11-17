package cn.hubindeveloper.rpc.server.proxy.JDKProxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;

@Slf4j
public class JDKProxyFactory {
    public static Object getProxy(Object target){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new JDKProxyHandler(target)
        );
    }

    public static void main(String[] args) {
        JDKSmsService JDKSmsService = (JDKSmsService) JDKProxyFactory.getProxy(new JDKSmsServiceImpl());
        String message = JDKSmsService.send("java");
        log.info("return message: {}", message);
    }
}
