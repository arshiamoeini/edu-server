package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class RealTime {
    private static RealTime instance;

    private JPanel panel;
    private JLabel DateTime;

    static {
        instance = new RealTime();
    }
    public RealTime() {
        /*
        idea of
            https://stackhowto.com/how-to-add-real-time-date-and-time-in-jframe/
         */
        DateTime.setText(now());

        Timer t = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DateTime.setText(now());
            }
        });
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setInitialDelay(0);
        t.start();
    }

    public static JPanel getPanel() { return instance.getShowPanel(); }
    private JPanel getShowPanel() { return panel; }

    private String now() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }

    private static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static String dateAndTime(LocalDateTime dateTime) {
        return dateTime.format(myFormatObj);
    }
    public static LocalDateTime readLocalDateTime(String text) {
        return LocalDateTime.from(myFormatObj.parse(text));
    }
    public static DateTimeFormatter myFormatObjWeek = DateTimeFormatter.ofPattern("EEE hh:mm a");
    public static String weekDayAndTime(LocalDateTime time) {
        return time.format(myFormatObjWeek);
    }
}
