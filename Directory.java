package com.mycompany.project32;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Directory extends FileSystemItem {
    private BinaryTreeNode<FileSystemItem> root;

    public Directory(String name, String accessLevel, String lastModifiedDate) {
        super(name, accessLevel, 0, lastModifiedDate);
        this.root = null; 
    }

    public BinaryTreeNode<FileSystemItem> getRoot() {
        return root;
    }
    public void setRoot(BinaryTreeNode<FileSystemItem> root) {
        this.root = root;
    }


    public void addContent(FileSystemItem item) {
        root = addRecursive(root, item);
        setSize(calculateSize());
        updateLastModifiedDate();
    }

    private BinaryTreeNode<FileSystemItem> addRecursive(BinaryTreeNode<FileSystemItem> current, FileSystemItem item) {
        if (current == null) {
            return new BinaryTreeNode<>(item);
        }

        if (item.getName().compareTo(current.getData().getName()) < 0) {
            current.setLeft(addRecursive(current.getLeft(), item));
        } else {
            current.setRight(addRecursive(current.getRight(), item));
        }

        return current;
    }

    public void removeContent(String name) {
        root = removeRecursive(root, name);
        setSize(calculateSize()); 
        updateLastModifiedDate(); 
    }

    private BinaryTreeNode<FileSystemItem> removeRecursive(BinaryTreeNode<FileSystemItem> current, String name) {
       if (current == null) {
        return null; 
    }

    if (name.equals(current.getData().getName())) {
        if (current.getLeft() == null && current.getRight() == null) {
            return null; 
        }
        if (current.getLeft() == null) {
            return current.getRight(); 
        }
        if (current.getRight() == null) {
            return current.getLeft(); 
        }

        FileSystemItem smallestValue = findSmallestValue(current.getRight()); 
        current.setData(smallestValue);
        current.setRight(removeRecursive(current.getRight(), smallestValue.getName())); 
        return current;
    }
    if (name.compareTo(current.getData().getName()) < 0) {
        current.setLeft(removeRecursive(current.getLeft(), name)); 
    } else {
        current.setRight(removeRecursive(current.getRight(), name)); 
    }

    return current; 
        
    }

    private FileSystemItem findSmallestValue(BinaryTreeNode<FileSystemItem> root) {
        return root.getLeft() == null ? root.getData() : findSmallestValue(root.getLeft());
    }
    

    public void displayContents() {
        traverseAndPrint(root);
    }

    private void traverseAndPrint(BinaryTreeNode<FileSystemItem> node) {
        if (node != null) {
            traverseAndPrint(node.getLeft());
            System.out.println(node.getData());
            traverseAndPrint(node.getRight());
        }
    }
    private List<FileSystemItem> traverseAndCollect(BinaryTreeNode<FileSystemItem> node) {
    List<FileSystemItem> items = new ArrayList<>();
    if (node != null) {
        items.addAll(traverseAndCollect(node.getLeft()));
        items.add(node.getData());
        items.addAll(traverseAndCollect(node.getRight()));
    }
    return items;
}

    @Override
    public double calculateSize() {
        return calculateSizeRecursive(root);
    }

    private double calculateSizeRecursive(BinaryTreeNode<FileSystemItem> node) {
        if (node == null) {
            return 0;
        }
        return node.getData().calculateSize()
                + calculateSizeRecursive(node.getLeft())
                + calculateSizeRecursive(node.getRight());
    }
   

    private void updateLastModifiedDate() {
        String latestDate = getLastModifiedDate(); 
        for (FileSystemItem item : traverseAndCollect(root)) {
            if (item.getLastModifiedDate().compareTo(latestDate) > 0) {
                latestDate = item.getLastModifiedDate(); 
            }
        }
        setLastModifiedDate(latestDate); 
    }

    @Override
    public String displayPath() {
        return "/" + getName(); 
    }

    @Override
    public void displayInfo() {
        System.out.println("Directory: " + getName());
        System.out.println("Access Level: " + getAccessLevel());
        System.out.println("Last Modified Date: " + getLastModifiedDate());
        System.out.println("Size: " + calculateSize() + " bytes");
        System.out.println("Contents:");
        displayContents();
    }
    
    
}
