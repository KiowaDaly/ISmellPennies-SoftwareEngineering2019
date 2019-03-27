//this method


package ClassLoader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class myClassLoader {
    private static String CLASS_FOLDER;
    //constructor reading in filepath of class
    public myClassLoader(String filepath){ this.CLASS_FOLDER = filepath; }

    public Class getClassFromFile(String classname) throws ClassNotFoundException, MalformedURLException {
        URLClassLoader url_loader = new URLClassLoader(new URL[]{
            new URL("file://"+CLASS_FOLDER)});

        return url_loader.loadClass(classname);
    }

}
