package com.julianogv.androiddiskreport.database;

/**
 * Created by juliano.vieira on 15/01/14.
 */
public class FileEntity {
    private Integer id;
    private String path;
    private Integer length;
    private Integer isDirectory;
    private Integer parentId;

    private Integer fullLength;

    public FileEntity(){

    }

    public FileEntity(Integer id, String path, Integer isDirectory, Integer length, Integer parentId){
        this.setId(id);
        this.setIsDirectory(isDirectory);
        this.setLength(length);
        this.setParentId(parentId);
        this.setPath(path);

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getIsDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(Integer isDirectory) {
        this.isDirectory = isDirectory;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getFullLength() {
        return fullLength;
    }

    public void setFullLength(Integer fullLength) {
        this.fullLength = fullLength;
    }
}
