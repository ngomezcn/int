package com.staxrt.tutorial.util;

import java.io.FileInputStream;
import java.util.Properties;

public class AppProps {
    static private final String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    static private final String configPath = rootPath + "application.properties";
    static private final Properties appProps = new Properties();

    static public String getProperty(String prop) throws Exception{
        appProps.load(new FileInputStream(AppProps.configPath));
        return appProps.getProperty(prop);
    }
}