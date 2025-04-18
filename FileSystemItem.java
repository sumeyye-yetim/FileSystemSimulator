package com.mycompany.project32;

import java.util.List;

public abstract class FileSystemItem {

    private String name;
    private String accessLevel;
    private double size;
    private String lastModifiedDate;

    public FileSystemItem(String name, String accessLevel, double size, String lastModifiedDate) {
        this.name = name;
        this.accessLevel = accessLevel;
        this.size = size;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }


    public abstract double calculateSize(); 

    public abstract String displayPath();  

    public abstract void displayInfo();   

    
    public String getExtension() {
        return null; // Here null is returned by default because it is not valid for Directory
    }

    
    public List<FileSystemItem> getContents() {
        return null; // Here null is returned by default because it is not valid for File
    }
}
