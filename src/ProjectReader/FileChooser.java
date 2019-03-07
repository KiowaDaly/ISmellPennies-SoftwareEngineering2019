package ProjectReader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class FileChooser {
   //open file explorer

    public Class selectFile() {

        JFrame myComponent = new JFrame();
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".java files","java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(myComponent);
        if(returnVal == JFileChooser.APPROVE_OPTION){

            System.out.println("You chose to open this file: "+chooser.getSelectedFile().getName());

        }

        return null;
    }
}

