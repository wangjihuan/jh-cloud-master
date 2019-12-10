package com.wang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangjihuan
 * @date 2019/11/19.
 */
public class JDKDynamicProxy implements InvocationHandler {
    private Class target;

    public JDKDynamicProxy(Class target) {
        this.target = target;
    }

    public <T>T getProxy() {
        return (T) Proxy.newProxyInstance(JDKDynamicProxy.class.getClassLoader(), new Class[]{target}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Do something before");
//        Object result = method.invoke(target, args);
        System.out.println("正在做某事,...");
        System.out.println("Do something after");
        return null;
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        Subject proxy = new JDKDynamicProxy(Subject.class).getProxy();
        proxy.doSomething();
    }

}
