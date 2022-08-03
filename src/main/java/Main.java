import DATE_BASE.InitialData;
import GUI.MainFrame;
import GUI.RealTime;
import LOGIC.Command;
import LOGIC.Logger;

import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        Logger.Init();
        System.out.println(RealTime.readLocalDateTime("1400-12-02 12:30:00"));
        RealTime.dateAndTime(LocalDateTime.now());
        new InitialData();
        MainFrame mainFrame = new MainFrame();
        Command.setFrame(mainFrame);
    //    InitialData.save();
    }
}
