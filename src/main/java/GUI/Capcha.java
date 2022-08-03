package GUI;

import javax.swing.*;

public class Capcha {
    int code;
    ImageIcon image;

    public Capcha(String code, ImageIcon image) {
        this.code = Integer.parseInt(code);
        this.image = image;
    }

    public ImageIcon getImageIcon() {
        return image;
    }

    public int getCode() {
        return code;
    }
}
