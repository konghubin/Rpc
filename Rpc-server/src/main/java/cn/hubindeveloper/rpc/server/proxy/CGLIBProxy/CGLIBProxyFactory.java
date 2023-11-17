package cn.hubindeveloper.rpc.server.proxy.CGLIBProxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CGLIBProxyFactory {
    public static Object getProxy(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new CGLIBProxyMethodInterceptor());
        return enhancer.create();
    }

    public static void main(String[] args) {
        CGLIBSmsService smsService = (CGLIBSmsService) CGLIBProxyFactory.getProxy(CGLIBSmsService.class);
        String message = smsService.send("java");
        log.info("return message: {}", message);
    }
}
