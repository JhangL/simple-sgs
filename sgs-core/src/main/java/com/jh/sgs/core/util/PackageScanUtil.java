package com.jh.sgs.core.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;

public class PackageScanUtil {
    public static List<Class<?>> scanPackage(String scan) throws  SecurityException, IOException, ClassNotFoundException {
        // 结果 class
        List<Class<?>> result = null ;
        // 把 . 转换成 \ 因为下面是文件操作
        scan = scan.replaceAll("\\.", "/");
        Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(scan);
        while(dirs.hasMoreElements()) {
            URL url = dirs.nextElement() ;
            if(url.getProtocol().equals("file")) {
                List<File> classes = new ArrayList<File>();
                // 递归 变量路径下面所有的 class文件
                listFiles(new File(url.getFile()),classes);
                // 加载我们所有的 class文件 就行了
                result= loadeClasses(classes,scan);
            }else if(url.getProtocol().equals("jar")) {
                // 等下再说
                //file:/D:/dev/scala_pro/test/src/jedis-2.8.0.jar!/redis
                JarURLConnection urlConnection = (JarURLConnection) url.openConnection();
// 从此jar包 得到一个枚举类
                Enumeration<JarEntry> entries = urlConnection.getJarFile().entries();
// 遍历jar
                while (entries.hasMoreElements()) {
                    // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                    JarEntry entry = entries.nextElement();
                    //得到该jar文件下面的类实体
                    if(entry.getName().endsWith(".class")) {
                        // 因为scan 就是/  ， 所有把 file的 / 转成  \   统一都是：  /
                        String fPath = entry.getName().replaceAll("\\\\","/") ;
                        // 把 包路径 前面的 盘符等 去掉
                        String packageName = fPath.substring(fPath.lastIndexOf(scan));
                        // 去掉后缀.class ，并且把 / 替换成 .    这样就是  com.hadluo.A 格式了 ， 就可以用Class.forName加载了
                        packageName = packageName.replace(".class","").replaceAll("/", ".");
                        // 根据名称加载类
//            			System.err.println(packageName);
//            			System.err.println(Class.forName(packageName).getName());
                    }
                }
            }

        }
        // 打印结果
       return result;
    }

    private static List<Class<?>> loadeClasses(List<File> classes, String scan) throws ClassNotFoundException {
        List<Class<?>> clazzes = new ArrayList<Class<?>>();
        for(File file : classes) {
            // 因为scan 就是/  ， 所有把 file的 / 转成  \   统一都是：  /
            String fPath = file.getAbsolutePath().replaceAll("\\\\","/") ;
            // 把 包路径 前面的 盘符等 去掉 ， 这里必须是lastIndexOf ，防止名称有重复的
            String packageName = fPath.substring(fPath.lastIndexOf(scan));
            // 去掉后缀.class ，并且把 / 替换成 .    这样就是  com.hadluo.A 格式了 ， 就可以用Class.forName加载了
            packageName = packageName.replace(".class","").replaceAll("/", ".");
            // 根据名称加载类
            clazzes.add(Class.forName(packageName));
        }
        return clazzes ;

    }

    /** * 查找所有的文件 * * @param dir 路径 * @param fileList 文件集合 */
    private static void listFiles(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                listFiles(f, fileList);
            }
        } else {
            if(dir.getName().endsWith(".class")) {
                fileList.add(dir);
            }
        }
    }
}
