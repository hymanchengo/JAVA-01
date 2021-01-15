package com.hymanting.jvm.homework;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Author hxchen
 * @Date 2021/1/15
 */
public class HymanClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            // load and init Class jvm.Hello
            Class<?> helloClass = new HymanClassLoader().findClass("Hello");
                    //.newInstance();
            helloClass.getDeclaredMethod("hello").invoke(helloClass.newInstance());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        Path xlassFilePath = Paths.get("C:\\Users\\Hyman\\Desktop\\javatrain\\Hello\\Hello.xlass");
        try {
            byte[] data = Files.readAllBytes(xlassFilePath);
            for(int i = 0; i < data.length; i++) {
                data[i] = (byte) (255 - data[i]);
            }
            return defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
