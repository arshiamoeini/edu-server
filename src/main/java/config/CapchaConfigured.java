package config;

import GUI.Capcha;

import javax.swing.*;
import java.awt.*;

public interface CapchaConfigured {
    default Capcha getImage(int randomNumber, int width, int height){
        Object[] imageData = ConfigHandler.getInstance().getCapcha(randomNumber);
        String capchaName = (String) imageData[0];
        Image image = ((ImageIcon) imageData[1]).getImage();
        ImageIcon capchaImage = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return new Capcha(capchaName.substring(0, capchaName.length() - 4), capchaImage);
    }
    abstract void generateCapcha();
}
