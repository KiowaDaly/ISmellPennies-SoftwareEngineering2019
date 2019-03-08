package UI;

import ProjectReader.FileChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectFileButton extends JButton {

    public SelectFileButton(){
        JButton button = new JButton("Select Project");
        button.setBounds(50,100,95,30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooser file = new FileChooser();
                file.selectFolder();
            }
        });
    }
}
