package com.tedu.java.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {
    // 1、获取class对象多种方式
    @Test
    public void test01() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 类名.class
        Class clazz1 = Car.class;
        // 对象.getClass()
        Class clazz2 = new Car().getClass();
        // Class.forName(“全路径”)
        Class clazz3 = Class.forName("com.tedu.java.reflect.Car");
        Car car = (Car) clazz3.getDeclaredConstructor().newInstance();
        System.out.println(car);
    }

    // 2、获取构造方法
    @Test
        public void test002() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz1 = Car.class;
        // 获取public修饰的构造方法
        //Constructor[] constructors = clazz1.getConstructors();
        // 获取所有的构造方法，包括private
        Constructor[] constructors = clazz1.getDeclaredConstructors();
        for (Constructor c : constructors){
            System.out.println(c.getName()+c.getParameterCount());
        }
        // 制定有参构造构建对象
        // 构造public
        Constructor c1 = clazz1.getConstructor(String.class, int.class, String.class);
        Car car1 = (Car)c1.newInstance("夏利", 10, "红色");
        System.out.println(car1);
        // 构造private
        Constructor c2 = clazz1.getDeclaredConstructor(String.class, int.class, String.class);
        // 设置允许访问
        c2.setAccessible(true);
        Car car2= (Car)c1.newInstance("zyy", 10, "红色");
        System.out.println(car2);

    }
    // 3、获取属性
    @Test
    public void test03() throws Exception{
        Class clazz1 = Car.class;
        Car car = (Car)clazz1.getDeclaredConstructor().newInstance();
        // 获取所有的public属性
        Field[] fields = clazz1.getFields();
        // 获取所有属性(包含私有属性)
        Field[] DFields = clazz1.getDeclaredFields();
        for(Field field:DFields){
            if("name".equalsIgnoreCase(field.getName())){
                // 设置允许访问
                field.setAccessible(true);
                field.set(car,"五菱宏光");
            }
            System.out.println(field.getName());
            System.out.println(car);
        }
    }
    // 4、获取方法
    @Test
    public void test04() throws Exception{
        Car car = new Car("奔驰",10,"黑色");
        Class<? extends Car> clazz = car.getClass();
        // public
        Method[] methods = clazz.getMethods();
        for (Method method:methods){
            System.out.println(method.getName());
            // 执行某个方法
            if("toString".equalsIgnoreCase(method.getName())){
                String invoke = (String)method.invoke(car);
                System.out.println(invoke);
            }
        }
        System.out.println("*********************************");
        // private方法
        Method[] DMethods = clazz.getDeclaredMethods();
        for (Method Dmethod:DMethods){
            System.out.println(Dmethod.getName());
            // 执行某个方法
            if("run".equalsIgnoreCase(Dmethod.getName())){
                Dmethod.setAccessible(true);
                String invoke = (String)Dmethod.invoke(car);
                System.out.println(invoke);
            }
        }

    }
}
