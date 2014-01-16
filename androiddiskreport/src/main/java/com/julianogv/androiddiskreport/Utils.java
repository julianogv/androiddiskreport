package com.julianogv.androiddiskreport;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juliano.vieira on 15/01/14.
 */
public class Utils {
    public static List<File> getFileList(File directory) {
        ArrayList<File> inFiles = new ArrayList<File>();

        if(!directory.isDirectory()){
            return inFiles;
        }

        File[] files = directory.listFiles();
        if(files == null){
            Log.d("JULIANOJ", "NULL FILE");
            return inFiles;
        }

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
