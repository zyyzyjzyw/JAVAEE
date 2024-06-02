package com.tedu.java.aop;

/**
 * @ClassName TestCal
 * @Description TODO
 * @Author zyy
 * @Date 2024/6/2 11:35
 * @Version 1.0
 */
public class TestCal {
    public static void main(String[] args) {
        // 创建代理对象(动态对象)
        ProxyFactory proxyFactory = new ProxyFactory(new CalCulatorImp());
        CalCulator proxy = (CalCulator)proxyFactory.getProxy();
        proxy.add(1,2);

    }
}
