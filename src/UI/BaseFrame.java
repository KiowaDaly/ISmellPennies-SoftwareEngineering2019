package UI;
import javax.swing.*;


public class BaseFrame extends JFrame {
    public BaseFrame(){
        JFrame frame = new JFrame("CODE SMELL");
        frame.add(new SelectFileButton());
        frame.setSize(800,800);
        frame.setLayout(null);
        frame.setVisible(true);


    }
}
