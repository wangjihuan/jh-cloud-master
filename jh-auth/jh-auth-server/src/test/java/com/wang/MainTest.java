package com.wang;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangjihuan
 * @date 2019/11/19.
 */
public class MainTest {

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("wang", "1");
        String reVal = map.put("wang", "1");
        System.out.println(reVal);

//        Thread mainT = Thread.currentThread();
//        Thread  thread= new Thread(() -> {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            LockSupport.unpark(mainT);
//        });
//        thread.start();
//        LockSupport.park();
//        System.out.println("hhaa");

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("等待了=====");
                condition.await();
                System.out.println("收到通知了=====");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(5000);
                condition.signalAll();
                System.out.println("发送通知了=====");
            } catch (InterruptedException e) {
            } finally {
                lock.unlock();
            }
        }).start();
        ProxyInterface proxyInterface = null;
        try {
            proxyInterface = ProxyInterface.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Proxy.newProxyInstance(proxyInterface.getClass().getClassLoader(), proxyInterface.getClass().getInterfaces(), new TxHandler());
    }


    static class  TxHandler implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }

    interface ProxyInterface{
        String eat(String str);
    }
}
