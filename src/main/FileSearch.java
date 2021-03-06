package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSearch {

    private final int SECONDS = 10;
    private String list = "";
    private ArrayList<String> result = new ArrayList<String>();
    private String searchfolder;
    private final String fileEnd = ".jpg";
    private ArrayList<String> fileNamesToSearch = new ArrayList<String>();
    private ArrayList<String> notFound = new ArrayList<String>();
    private File SAVEPATH;
    private ArrayList<String> allFiles = new ArrayList<String>();


    private int setFileNamesToSearch(ArrayList<String> fileNamesToSearch) {
        this.fileNamesToSearch = fileNamesToSearch;
        return this.fileNamesToSearch.size();
    }

    private void search() {
        File folder = new File(searchfolder).getAbsoluteFile();

        for (String searchFile : fileNamesToSearch) { 
            for (final String targetFile : allFiles) {
                String smallSearch = (searchFile + fileEnd).toLowerCase();
                
                if (smallSearch.equals( targetFile.toLowerCase()) ) {
                    Window.setText("Found: " + targetFile.toLowerCase());
                    
                    if (folder.isDirectory()) {
                        result.add(targetFile.toLowerCase());
                        
                        copyFile(new File(searchfolder + "/"+ targetFile.toLowerCase()) );
                    }
                }
            }
        }
        Window.setText("== FOUND: " + result.size() + " matches! ==");
    }

    private boolean setFolderContent(File directory) {
        if (directory.isDirectory()) {
            //do you have permission to read this directory?	
            if (directory.canRead()) {
                for (File temp : directory.listFiles()) {
                    if (temp.isDirectory()) {
                        setFolderContent(temp);
                    } else {
                        allFiles.add(temp.getName().toLowerCase());
                    }
                }
            } else {
                System.out.println(directory.getAbsoluteFile() + "Permission Denied");
                return false;
            }
        }
        System.out.println("allfiles: " + allFiles.size());
        return true;
    }

    public FileSearch(String searchfolder, ArrayList<String> fileNamesToSearch, File SAVEPATH) {
        this.searchfolder = searchfolder;
        this.SAVEPATH = SAVEPATH;

        System.out.println( setFileNamesToSearch(fileNamesToSearch) );
        setFolderContent(new File(searchfolder) );
        search();              
        
        writeFile( getMissedFiles() );
        Window.setText("== Didn't find a total of " + notFound.size() + " files.. ==");
    }

    private void copyFile(File absoluteFile) {
        try {
            File saveTo = new File(SAVEPATH, absoluteFile.getName());
            Files.copy(absoluteFile.toPath(), saveTo.toPath());
            System.out.println("COPY DONE!");
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    private void writeFile(ArrayList<String> write) {
        String filename = "_MISSED FILES.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                new File(SAVEPATH, filename)), "utf-8")
            );

            for (String line : write) {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException ex) {
            // report
        } finally {
            try {
                System.out.println("Missed logfile written and saved.");
                Process p = new ProcessBuilder("notepad", SAVEPATH + "\\" + filename).start();
            } catch (IOException ex) {
                Logger.getLogger(FileSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private ArrayList<String> getMissedFiles() {
        notFound = fileNamesToSearch;
        
        for (int i = 0; i < notFound.size(); i++) {
            for (int j = 0; j < result.size(); j++) {
                if ((notFound.get(i) + fileEnd).toLowerCase().equals(result.get(j))) {
                    notFound.remove(i);
                }
            }
        }
        
        return notFound;
    }
}
