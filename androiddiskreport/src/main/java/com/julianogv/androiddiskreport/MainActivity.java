package com.julianogv.androiddiskreport;

import android.app.Activity;
import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.julianogv.androiddiskreport.database.FileDbDAO;
import com.julianogv.androiddiskreport.database.FileDbHelper;
import com.julianogv.androiddiskreport.database.FileEntity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    FileDbDAO dbDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbDAO = new FileDbDAO(this);

        File src = new File("/storage/emulated/0/Pictures");
        //saveFileListIntoDatabase(src);
        List<FileEntity> topLevelDirs = dbDAO.getTopLevelDirectories (src.getPath());

        //set directory full length
        for(int i=0; i < topLevelDirs.size(); i++){
            topLevelDirs.get(i).setFullLength(dbDAO.getDirectorySize(topLevelDirs.get(i).getPath()));
        }

        //specify where the pie chart will be created
        LinearLayout lv1 = (LinearLayout) findViewById(R.id.linear);
        PieChartView pieChartView = new PieChartView(this, topLevelDirs);
        lv1.addView(pieChartView);



        Integer dirSize = dbDAO.getDirectorySize(src.getPath());
        Log.d("JULIANOJ", "Directory size: " + dirSize);
    }

    public void saveFileListIntoDatabase(File src){
        List<File> files = Utils.getFileList(src);

        Log.d("JULIANOJ", "FILES FOUND: " + files.size());

        if(files.size() > 0){
            //insert src path
            dbDAO.insert(src.getPath(), ((Long)src.length()).intValue(), 1, null);
            Integer parentId;

            for(int i=0; i < files.size(); i++){

                if(i % 1000 == 0){
                    Log.d("JULIANOJ", "CURRENT POS: " + i);
                }
                Long longLength = files.get(i).length();
                Integer isDirectory = files.get(i).isDirectory() == true ? 1 : 0;
                parentId = dbDAO.getOrCreateParentIdByPath(files.get(i).getParent());
                dbDAO.insert(files.get(i).getPath(), longLength.intValue(), isDirectory, parentId);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
