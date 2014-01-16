package com.julianogv.androiddiskreport.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juliano.vieira on 15/01/14.
 */
public class FileDbDAO {
    private SQLiteDatabase database;
    private FileDbHelper dbHelper;
    Cursor cursor;
    Context mContext;

    public FileDbDAO(Context context) {
        dbHelper = new FileDbHelper(context);
        this.database = dbHelper.getWritableDatabase();
        mContext = context;
    }

    public Integer insert(String path, Integer length, Integer isDirectory, Integer parentId){
        ContentValues cv = new ContentValues();
        cv.put("path", path);
        cv.put("length", length);
        cv.put("isDirectory", isDirectory);
        cv.put("parentId", parentId);
        long insertedId = database.insert(FileDbHelper.TABLE_NAME, null, cv);
        return ((Long)insertedId).intValue();
    }

    public FileEntity cursorToFileEntity(Cursor cursor){
        cursor.moveToFirst();
        return new FileEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                cursor.getInt(3), cursor.getInt(4));
    }

    public List<FileEntity> cursorToFileEntityList(Cursor cursor){
        List<FileEntity> files = new ArrayList<FileEntity>();
        cursor.moveToFirst();
        Log.d("JULIANOJ", "Cursor Count: " + cursor.getCount());
        for(int i=0; i < cursor.getCount(); i++){
            files.add(new FileEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4)));
            cursor.moveToNext();
        }
        Log.d("JULIANOJ", "Cursor Count: " + cursor.getCount());
        return files;
    }


    public Integer getOrCreateParentIdByPath(String path, boolean recursiveSearchParent){
        if(path == null){
            return null;
        }

        cursor = database.rawQuery("select id, path, isDirectory, length, parentId from fileData where path = '" + path + "'", null);
        if(cursor.getCount() > 0){
            return cursorToFileEntity(cursor).getId();
        }else{
            File file = new File(path);
            if(recursiveSearchParent == true){
                return insert(path, null, 1, getOrCreateParentIdByPath(file.getParent(), true));
            }else{
                return insert(path, null, 1, null);
            }
        }
    }

    public Integer getDirectorySize(String path){
        cursor = database.rawQuery("select fd2.id, fd2.path, fd2.isDirectory, fd2.length, fd2.parentId"
                + " from filedata fd1"
                + " inner join filedata fd2 on fd1.id = fd2.parentid"
                + " where fd1.path = '" + path + "'", null);


        Integer dirSize = 0;

        if (cursor.getCount() > 0) {
            List<FileEntity> fileList = cursorToFileEntityList(cursor);
            for(FileEntity file : fileList){
                if(file.getIsDirectory() == 1){
                    dirSize += getDirectorySize(file.getPath());
                }else{
                    dirSize += file.getLength();
                }
            }
            //Toast.makeText(mContext, “Toast”, Toast.LENGTH_SHORT).show();
        }
        return dirSize;
    }

    /*public void getFiles() {
        cursor = database.rawQuery("select id, path, isDirectory, length, parentId from fileData", null);
        if (cursor.getCount() > 0) {
            cursorToFileList(cursor);
            //Toast.makeText(getApplicationContext(), “Exemplo Toast”, Toast.LENGTH_SHORT).show();
        }
    }*/
}
