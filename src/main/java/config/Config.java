package config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config extends Properties {
    public Config() {
    }

    public int getInt(String key) {
        return Integer.parseInt(getProperty(key));
    }


   // public boolean getBool(String key) {
     //   return Boolean.parseBoolean(table.get(key));
   // }
}
