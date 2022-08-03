package config;

import javax.swing.*;
import java.awt.*;

public interface Configured {
    default Config getConfig(String name) {
        return ConfigHandler.getInstance().getConfig(name);
    }
    default ImageIcon getImage(String name, int width, int height) {
        Image image = ConfigHandler.getInstance().getImage(name).getImage();
        return new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
    }
    default ImageIcon getImage(String name) {
        return null;
    }
}
