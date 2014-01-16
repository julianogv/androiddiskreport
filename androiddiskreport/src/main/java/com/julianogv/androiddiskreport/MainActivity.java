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

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout lv1 = (LinearLayout) findViewById(R.id.linear);
        PieChartView pieChartView = new PieChartView(this);
        lv1.addView(pieChartView);

        /*try {
            Process p =  Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        List<File> files = Utils.getFileList(new File("/storage/emulated/0/teste"));

        FileDbDAO dbDAO = new FileDbDAO(this);
        Integer parentId;

        for(File file: files){
            Long longLength = file.length();
            Integer isDirectory = file.isDirectory() == true ? 1 : 0;
            parentId = dbDAO.getOrCreateParentIdByPath(file.getParent());
            dbDAO.insert(file.getPath(), longLength.intValue(), isDirectory, parentId);
        }

        Integer dirSize = dbDAO.getDirectorySize("/");
        Log.d("JULIANOJ", "Directory size: " + dirSize);
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
