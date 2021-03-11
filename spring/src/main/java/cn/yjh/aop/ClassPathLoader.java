package cn.yjh.aop;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-06 22:11
 */
public class ClassPathLoader {

    private static Method addURL = initAddMethod();

    public static URLClassLoader classloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    /**
     * 初始化addUrl 方法.
     *
     * @return 可访问addUrl方法的Method对象
     */
    private static Method initAddMethod() {
        try {
            // 反射获取addURL方法
            Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
            // 设置访问权限
            add.setAccessible(true);
            return add;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载jar classpath。
     */
    public static void loadClasspath() {
        List<String> files = getJarFiles();
        if (files == null)
            return;
        for (String f : files) {
            loadClasspath(f);
        }

        List<String> resFiles = getResFiles();
        if (resFiles == null)
            return;
        for (String r : resFiles) {
            loadResourceDir(r);
        }
    }

    private static void loadClasspath(String filepath) {
        File file = new File(filepath);
        loopFiles(file);
    }

    private static void loadResourceDir(String filepath) {
        File file = new File(filepath);
        loopDirs(file);
    }

    /** */
    /**
     * 循环遍历目录，找出所有的资源路径。
     *
     * @param file
     *            当前遍历文件
     */
    private static void loopDirs(File file) {
        // 资源文件只加载路径
        if (file.isDirectory()) {
            addURL(file);
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopDirs(tmp);
            }
        }
    }

    /**
     * 循环遍历目录，找出所有的jar包。
     *
     * @param file
     *            当前遍历文件
     */
    private static void loopFiles(File file) {
        if (file.isDirectory()) {
            File[] tmps = file.listFiles();
            for (File tmp : tmps) {
                loopFiles(tmp);
            }
        } else {
            if (file.getAbsolutePath().endsWith(".jar") || file.getAbsolutePath().endsWith(".zip")) {

                addURL(file);
            }
        }
    }

    /**
     * 通过filepath加载文件到classpath。
     *
     * @param filePath
     *            文件路径
     * @return URL
     * @throws Exception
     *             异常
     */
    private static void addURL(File file) {
        try {
            addURL.invoke(classloader, new Object[] { file.toURI().toURL() });
        } catch (Exception e) {
        }
    }

    /**
     *
     * 将当前classpath下jar.txt的清单jar明见加载到classpath中
     *
     * @return
     * @throws Exception
     */
    private static List<String> getJarFiles() {
        // TODO 从properties文件中读取配置信息 如果不想配置 可以自己new 一个List<String> 然后把 jar的路径加进去
        // 然后返回
        InputStream in = ClassLoader.getSystemResourceAsStream("jar.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line = null;
        List<String> list = new ArrayList<String>();
        try {
            line = br.readLine();
            while (line != null) {
                list.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    /**
     * 从配置文件中得到配置的需要加载classpath里的资源路径集合
     *
     * @return
     */
    private static List<String> getResFiles() {
        // TODO 从properties文件中读取配置信息略 如果不想配置 可以自己new 一个List<String> 然后把
        // jar的路径加进去 然后返回 额 如果没有资源路径为空就可以了
        List<String> list = new ArrayList<>();
        URL resources = ClassPathLoader.class.getClass().getResource("/");
        String path = resources.getPath();
        list.add(path)
        ;
        return list;
    }
}
