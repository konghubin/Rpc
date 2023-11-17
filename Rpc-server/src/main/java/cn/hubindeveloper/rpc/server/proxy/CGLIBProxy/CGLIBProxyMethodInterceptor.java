package cn.hubindeveloper.rpc.server.proxy.CGLIBProxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
public class CGLIBProxyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("before method {}", method.getName());
        Object result = proxy.invokeSuper(obj, args);
        log.info("After method {}", method.getName());
        return result;
    }
}
