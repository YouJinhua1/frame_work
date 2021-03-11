package cn.yjh.aop;

import test.service.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ClassLoaderTest {

    enum JARClassLoader{
        factory;
        JARClassLoader(){
            this.loader = new ClassLoader(){

                private String classpath = this.getClass().getResource("/").getPath();;

                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    synchronized (getClassLoadingLock(name)) {
                        Class<?> clazz = findLoadedClass(name);
                        ClassLoader parent = this.getClass().getClassLoader().getParent();
                        if (clazz == null) {
                            clazz = findClass(name);
                            if (clazz == null) {
                                if (parent != null) {
                                    clazz = super.loadClass(name, false);
                                }
                            }
                        }
                        return clazz;
                    }
                }

                @Override
                protected Class<?> findClass(String name) {
                    Class clazz = null;
                    byte[] classData = null;
                    String className = name.substring(name.lastIndexOf(".")+1,name.length());
                    String sourcePath = classpath + File.separator + className + ".class";
                    File file = new File(sourcePath);
                    if (file.exists()){
                        FileInputStream in = null;
                        try {
                            in = new FileInputStream(file);
                            FileChannel channel = in.getChannel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(in.available());
                            channel.read(byteBuffer);
                            classData = byteBuffer.array();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }finally {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (classData != null) {
                        clazz = defineClass(name, classData, 0, classData.length);
                    }
                    return clazz;
                }

            };
        }

        private ClassLoader loader;

        public ClassLoader getLoader() {
            return loader;
        }
    }


    public static void main(String[] args) throws Exception {
        // 测试自定义的类加载器是否是单例的
        /*for (int i = 0; i < 1000; i++) {
            new Thread(()->{
                System.out.println(JARClassLoader.factory.getLoader());
            }).start();
        }*/

        // 测试自定义类加载器是否能使用
        /*Class<?> clazz = null;
        try {
            clazz = JARClassLoader.factory.getLoader().loadClass("java.lang.String");
            //利用反射获取print方法
           *//* Method method = clazz.getDeclaredMethod("print");
            Object myString = clazz.newInstance();
            method.invoke(myString);
            System.out.println(clazz.getClassLoader());*//*
            clazz = JARClassLoader.factory.getLoader().loadClass("java.lang.String");
            System.out.println(clazz.getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        // 创建一个URL数组
        URL[] urls = {
                new URL("file:D:\\maven-repository\\asm\\asm\\3.3.1\\asm-3.3.1.jar")

        };
        URLClassLoader loader = URLClassLoader.newInstance(urls);
        Class<?> clazz = Class.forName("java.net.URLClassLoader");
        Method method = clazz.getDeclaredMethod("addURL",URL.class);

        method.setAccessible(true);
        method.invoke(loader,new URL(("file:D:\\maven-repository\\cglib\\cglib\\2.2.2\\cglib-2.2.2.jar")));
        method.invoke(loader,new URL(("file:D:\\workspace\\frame_work\\spring\\target\\classes")));

        clazz = loader.loadClass("org.objectweb.asm.AnnotationVisitor");
        System.out.println(clazz.getClassLoader());
        clazz = loader.loadClass("net.sf.cglib.beans.BeanMap");


        System.out.println(clazz.getName());
        clazz = loader.loadClass("test.service.UserService");
        Object o =  clazz.getDeclaredConstructor().newInstance();
        Method method1 = clazz.getDeclaredMethod("getUser",String.class);
        Method[] declaredMethods = clazz.getDeclaredMethods();
//        for(Method method1:declaredMethods){
//            if(method1.getName().equals("getUser")){
//                method1.invoke(o,"YouJinhua");
//            }
//        }
        clazz.getDeclaredMethod("getUser",String.class);;
        method1.invoke(o,"YouJinhua");
        System.out.println(o.getClass().getClassLoader());
        System.out.println(clazz.getClassLoader());


    }
}
