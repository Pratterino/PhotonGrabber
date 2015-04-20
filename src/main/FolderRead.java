package main;


import java.io.*;
import javax.swing.JFileChooser;

/**
 *
 * @author Paste
 */
public class FolderRead {
    
    private File folder;   //Opened .txt-file.
    private String strLine;

    public String browse() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            folder = chooser.getSelectedFile();
            return folder.getAbsolutePath();
        }

        return "No folder selected.";
    }
    
    public FolderRead() {
        
    }
    
}
