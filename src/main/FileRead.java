package main;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import main.Window;

/**
 *
 * @author Paste
 */
public class FileRead {
    
    public ArrayList<String> list = new ArrayList<String>();   //Encrypted keys.
    
    private File file;   //Opened .txt-file.
    private String strLine;
    

    public String browse() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT File", "txt");
        chooser.setFileFilter(filter);
        
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            
            readLines();
            return file.getAbsolutePath();
        }

        return "No file selected.";
    }
    
    private void readLines() {
        try {
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                if ("".equals(strLine) || " ".equals(strLine)) {
                    print("Empty line..");
                } else {
                    list.add(strLine.trim());
                }
            }
            
            //Close the input stream
            in.close();
            print("Number of usable rows: " + list.size());
            print("Complete list: " + list);
            
        } catch (IOException e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        } 
    }

    private void print(Object text) {
        Window.setText(text.toString());
        System.out.println(text.toString());
    }
    
    public FileRead() {
        
    }

    String copy(String SEARCH, String SAVE) {
        FileSearch search = new FileSearch(SEARCH, list, new File(SAVE));
        return SAVE;
    }
}
