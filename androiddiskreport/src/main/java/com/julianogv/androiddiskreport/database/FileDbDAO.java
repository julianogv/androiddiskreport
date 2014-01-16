package com.julianogv.androiddiskreport.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

        for(int i=0; i < cursor.getCount(); i++){
            files.add(new FileEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getInt(4)));
        }
        return files;
    }


    public Integer getOrCreateParentIdByPath(String path){
        if(path == null){
            return null;
        }

        cursor = database.rawQuery("select id, path, isDirectory, length, parentId from fileData where path = '" + path + "'", null);
        if(cursor.getCount() > 0){
            return cursorToFileEntity(cursor).getId();
        }else{
            int parentId;

            File file = new File(path);

            parentId = insert(path, null, 1, getOrCreateParentIdByPath(file.getParent()));

            return parentId;
        }
    }

    public Integer getDirectorySize(String path){
        cursor = database.rawQuery("select id, path, isDirectory, length, parentId from fileData where path = " + path, null);
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
