package cn.hubindeveloper.rpc.server.proxy.JDKProxy;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@AllArgsConstructor
public class JDKProxyHandler implements InvocationHandler {
    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("before method {}", method.getName());
        Object result = method.invoke(target, args);
        log.info("After method {}", method.getName());
        return result;
    }
}
