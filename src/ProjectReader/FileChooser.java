package ProjectReader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;

public class FileChooser {
   //open file explorer
    Process myFile;
    public void selectFile() {
        JFrame myComponent = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images","jpg","gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(myComponent);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            System.out.println("You chose t open this file: "+chooser.getSelectedFile().getName());
        }
    }
}
