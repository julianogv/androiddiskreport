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

    public static float[] calculateData(float[] data) {
        // TODO Auto-generated method stub
        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }
        return data;
    }

}
