package com.tedu.java.aop;

/**
 * @ClassName CalCulatorImp
 * @Description TODO
 * @Author zyy
 * @Date 2024/6/1 10:47
 * @Version 1.0
 */
public class CalCulatorImp implements CalCulator{
    @Override
    public int add(int i, int j) {
        int result = i + j;
        System.out.println("方法内部 result = "+result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        System.out.println("方法内部 result = "+result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        System.out.println("方法内部 result = "+result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        System.out.println("方法内部 result = "+result);
        return result;
    }
}
