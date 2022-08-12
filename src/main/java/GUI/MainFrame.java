package GUI;

import shared.ConstructorData;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static final int MAIN_WIDTH = 800;
    public static final int MAIN_HEIGHT = 700;
    private PanelDesigner designer;

    public MainFrame() throws HeadlessException {
        //setting
        setSize(MAIN_WIDTH, MAIN_HEIGHT);
        setResizable(false);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();

        Rectangle mainScreen = gd[0].getDefaultConfiguration().getBounds();
        int upLeftCornerX = mainScreen.x + mainScreen.width / 2 - MAIN_WIDTH / 2;
        int upLeftCornerY = mainScreen.y + mainScreen.height / 2 - MAIN_HEIGHT / 2;
        setLocation(upLeftCornerX, upLeftCornerY);

        //JPanel p =  new JPanel();
     //   p.add(new List());
       /* List list = new List();
        Classroom classroom = Faculties.getInstance().getFaculty().getClassrooms().get(0);
        System.out.println(classroom == null);
*/
        setLayout(null);
        //setContentPane(new Container());
        //getContentPane().add(list, BorderLayout.CENTER);

   //     showLogin();
    //    setContentPane(new TemporaryScoresPage().getPanel());//new EducationalServicesDesigner().getPanel()); //ListDesigner.getInstance().getPanel());); //Login.getInstance().getLoginPane());  //new MainPage().getPane()););
        repaint();
        //setJMenuBar((new StudentMenus()).mb);//(new MainPage()).panel1);//RealTime.getPanel());//Login.getInstance().getLoginPane());

        revalidate();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void uploadPage() {
       // setContentPane(new MainPage().getPanel());
        repaint();
    }

    public void showLogin() {
    //    setContentPane(null);
        revalidate();
        repaint();
    }
    public void setContentPanel(PanelDesigner designer) {
        this.designer = designer;
        setContentPane(designer.getPanel());
    //    JPanel panel = new JPanel();
    //    panel.add(designer.getPanel());
     //   setContentPane(panel);
        repaint();
        revalidate();
        setVisible(true);
    }

    public void enterMainPage(ConstructorData constant, long userID) {
        OptionCentricText.setFaculties(constant.getFactories());
        OptionCentricText.setPrograms(constant.getPrograms());
        UserConstantInformation.getInstance().setUserData(constant);
        UserConstantInformation.getInstance().setUserId(userID);
        setContentPanel(new MainPage(() -> setContentPanel(Login.getInstance())));
    }
}
