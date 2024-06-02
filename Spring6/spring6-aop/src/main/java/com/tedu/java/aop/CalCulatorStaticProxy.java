package com.tedu.java.aop;

/**
 * @ClassName CalCulatorStaticProxy
 * @Description TODO
 * @Author zyy
 * @Date 2024/6/1 11:05
 * @Version 1.0
 */
public class CalCulatorStaticProxy implements CalCulator{
    // 被代理目标对象传递过来
    private CalCulator calCulator;

    public CalCulatorStaticProxy(CalCulator calCulator) {
        this.calCulator = calCulator;
    }

    @Override
    public int add(int i, int j) {
        // 附加功能由代理类中的代理方法来实现
        System.out.println("[日志] add 方法开始了，参数是：" + i + "," + j);

        // 通过目标对象来实现核心业务逻辑
        int addResult = calCulator.add(i, j);

        System.out.println("[日志] add 方法结束了，结果是：" + addResult);

        return addResult;
    }

    @Override
    public int sub(int i, int j) {
        return 0;
    }

    @Override
    public int mul(int i, int j) {
        return 0;
    }

    @Override
    public int div(int i, int j) {
        return 0;
    }
}
