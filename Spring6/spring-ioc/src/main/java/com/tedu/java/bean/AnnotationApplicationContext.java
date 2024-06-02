package com.tedu.java.bean;


import com.tedu.java.anno.Bean;
import com.tedu.java.anno.Di;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName AnnotationApplicationContext
 * @Description TODO
 * @Author zyy
 * @Date 2024/5/27 21:31
 * @Version 1.0
 */
public class AnnotationApplicationContext implements ApplicationContext{
  // 创建map集合，放bean对象
    private static Map<Class,Object> beanFactory  = new HashMap<>();

    private static String rootPath;


    // 返回对象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    // 设置包扫描规则
    // 当前包及其子包，哪个类有@Bean注解，把这个通过反射实例化
    // 创建有参数的构造，传递包的路径
    public  AnnotationApplicationContext(String beanPackage){
        // 把.替换成\
        String packagePath =
                beanPackage.replaceAll("\\.", "\\\\");
        // 获取包的绝对路径
        try {
            Enumeration<URL> resources =
                    Thread.currentThread().getContextClassLoader().getResources(packagePath);
          while (resources.hasMoreElements()){
              URL url =  resources.nextElement();
              String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
              rootPath = filePath.substring(0, filePath.length() - packagePath.length());
              // 包扫描
              loadBean(new File(filePath));

          }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 属性注入
        loadDi();
    }

    private void loadDi() {
        // 实例化对象在beanFactory的map集合里
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        // 遍历beanFactory的map集合
        for (Map.Entry<Class,Object> entry:entries){
            // 获取map集合中每个对象(value),每个对象属性获取到
            Object obj = entry.getValue();
            // 获取对象Class
            Class<?> clazz = obj.getClass();
            // 获取对象的属性
            Field[] declaredFields = clazz.getDeclaredFields();
            // 遍历得到每个对象属性数组,得到每个属性
            for (Field field:declaredFields){
                // 判断属性上面是否有@Di注解
                Di annotation = field.getAnnotation(Di.class);
                if (annotation!=null){
                    // 如果私有属性，设置可以设置值
                    field.setAccessible(true);
                    // 如果有@Di注解，把对象进行设置(注入)
                    try {
                        field.set(obj,beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    // 包扫描的过程,实例化
    private static void loadBean(File file) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // 判断当前是否是文件夹
        if(file.isDirectory()){
            // 获取文件夹里面的所有内容
            File[] childrenFiles = file.listFiles();
            if(childrenFiles == null || childrenFiles.length==0){
                // 判断文件夹里面是否为空,直接返回
                return;
            }
            // 如果文件夹里面不为空,便利文件夹中的所有的内容
            for (File child : childrenFiles){
                // 便利得到每个file对象,继续判断,如果还是文件夹,递归
                if (child.isDirectory()){
                    loadBean(child);
                }else{
                    // 遍历得到File对象不是文件夹,是文件
                    // 得到包路径+类名称  -- 字符串截取
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    // 判断当前文件类型是否.class
                    if(pathWithClass.contains(".class")){
                        // 如果是.class类型,把路径\替换成.  把.class去掉
                        String allName = pathWithClass.replaceAll("\\\\", ".").replaceAll(".class", "");
                        // 判断不是接口
                        Class<?> clazz = Class.forName(allName);
                        if(!clazz.isInterface()){
                            Bean annotation = clazz.getAnnotation(Bean.class);
                            if(annotation!=null){
                                // 实例化
                                Object instance = clazz.getDeclaredConstructor().newInstance();
                                // 判断当前嘞如果有借口，让接口class作为map的key
                                if(clazz.getInterfaces().length>0){
                                    // 把实例化之后的对象,放到Map中去
                                    beanFactory.put(clazz.getInterfaces()[0],instance);
                                }else {
                                    beanFactory.put(clazz,instance);
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
