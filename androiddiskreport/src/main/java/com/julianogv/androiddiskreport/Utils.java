package com.julianogv.androiddiskreport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juliano.vieira on 15/01/14.
 */
public class Utils {
    public static List<File> getFileList(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getFileList(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }
}
