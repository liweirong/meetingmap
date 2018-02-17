package com.iris.common.properties;

import java.util.Properties;
import java.io.*;

 
public class BasePropertyKeyValue {
	
    private final static String MAIL_PROPERTIES = "config/properties/mail.properties";
    
    private static Properties prop = null;

    
    public BasePropertyKeyValue() {
        if (prop == null) {
            loadProperties();
        }
    };

    private synchronized static void loadProperties() {
        try {
            // Open the props file
            InputStream is = BasePropertyKeyValue.class.getResourceAsStream("/" + MAIL_PROPERTIES);
            prop = new Properties();
            // Read in the stored properties
            prop.load(is);
        } catch (Exception e) {
            System.err.println("读取配置文件失败！！！");
            prop = null;
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        if (prop == null) {
            loadProperties();
        }
        return prop.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (prop == null) {
            loadProperties();
        }
        return prop.getProperty(key, defaultValue);
    }

    public static void main(String[] args) {
        String tt = BasePropertyKeyValue.getProperty("HOTEL_APP_SERVER_ADDRESS_POTAL");
        System.out.println("tt=======" + tt);
    }

}
