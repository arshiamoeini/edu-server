package GUI;

import config.CapchaConfigured;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CapchaGenerator implements CapchaConfigured {
    Random random = new Random();
    Capcha capcha;
    public CapchaGenerator() {
    }

    @Override
    public void generateCapcha() {
        capcha = getImage(random.nextInt(), 170, 25);
    }

    public ImageIcon getCapchaImage() {
        return capcha.getImageIcon();
    }

    public int gerCapchaCode() {
        return capcha.getCode();
    }
}
