
package com.mycompany.project32;

import java.util.Date;

public class File extends FileSystemItem{
    private String extension; 

    public File(String name, String extension, String accessLevel, String lastModifiedDate, double size) {
        super(name, accessLevel, size, lastModifiedDate);
        this.extension = extension;
    }

    
    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public double calculateSize() {
        return getSize(); 
    }

    @Override
    public String displayPath() {
        return getName(); 
    }

    @Override
    public void displayInfo() {
        System.out.println("File: " + getName());
        System.out.println("Extension: " + extension);
        System.out.println("Access Level: " + getAccessLevel());
        System.out.println("Last Modified Date: " + getLastModifiedDate());
        System.out.println("Size: " + getSize() + " bytes");
    }
}
