package ProjectReader;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;


public class FileChooser {
   //open file explorer

    //function that opens the users file explorer to select and return a folder
    public File selectFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(chooser.FILES_AND_DIRECTORIES);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

}

