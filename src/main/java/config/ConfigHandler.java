package config;

import GUI.Capcha;
import GUI.CapchaGenerator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler {
    static ConfigHandler instance;
    static {
        instance = new ConfigHandler();
    }

    public static String SOURCE = "src/main/resources/";
    public static String CAPCHA_SOURCE = SOURCE+"capcha image/";
    public static String SUFFIX_FILE = ".properties";
    public static Gson GSON;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        GSON = builder.create();
    }
    private static final Map<String, Config> table;
    static {
        Map<String, Config> temp = new HashMap<>();
       // temp.put(Court.class.getName(), loadConfig("hand cart"));
        table = Collections.unmodifiableMap(temp);
    }
    public static Config loadConfig(String fileName) {
        Config property = new Config();
        String address = SOURCE+fileName+SUFFIX_FILE;
        File add = new File(address);
        try {
            FileReader file = new FileReader(address);
            property.load(file);
            return property;
        } catch (Exception e) {
            try {
                FileOutputStream fos = new FileOutputStream(address);
                OutputStreamWriter isr = new OutputStreamWriter(fos,
                        StandardCharsets.UTF_8);

                //GSON.toJson(new Config(), isr);
                isr.close();
                return loadConfig(fileName);
            } catch (Exception err) {
                throw new RuntimeException(err);
            }
            //try again
           // return loadConfig(fileName);
        }
    }

    public ConfigHandler() {
    }

    public static ConfigHandler getInstance() {
        return instance;
    }
    public Config getConfig(String name) {
        return table.get(name);
    }

    public ImageIcon getImage(String dir) {
        return new ImageIcon(CAPCHA_SOURCE+dir+".jpg");
    }

    public Object[] getCapcha(int randomNumber) {
        File[] images = new File(CAPCHA_SOURCE).listFiles();
        File imageFile = images[Math.abs(randomNumber) % images.length];
        String capchaName = imageFile.getName();
        ImageIcon capchaImage = new ImageIcon(CAPCHA_SOURCE+capchaName+"/");
        return new Object[]{capchaName, capchaImage};
    }
}
