package GUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CellPane extends JPanel {
    private Component label;

    public CellPane(Component label) {
        this.label = label;
        setBackground(new Color(73, 144, 183, 144));
        setBorder(new LineBorder(Color.BLACK));
        add(label);
    }
    public CellPane(String text) {
        this(new JLabel(text));
    }
    public CellPane(String text, boolean editable) {
        this(textField(text, editable));
    }
    public static JTextArea textField(String text, boolean editable) {
        JTextArea textLabel = new JTextArea(text);
        textLabel.setEditable(editable);
        return textLabel;
    }
    public void setEditable() {
        if (label instanceof JTextArea) {
            ((JTextArea) label).setEditable(true);
        }
    }
    public String getText() {
        if (label instanceof JTextArea) {
            return ((JTextArea) label).getText();
        }
        return null;
    }
    public void setButton(JButton button) {
        removeAll();
        add(button);
        label = button;
    }

    public Component getLabel() {
        return label;
    }

    public void setText(String text) {
        ((JTextArea) label).setText(text == null ? "null" : text);
    }
}
