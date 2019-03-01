package ProjectReader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {
   //open file explorer

    public void selectFile() {
        JFrame myComponent = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".Class files","class");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(myComponent);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            System.out.println("You chose to open this file: "+chooser.getSelectedFile().getName());
        }
    }
}
