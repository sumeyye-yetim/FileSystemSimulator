package com.mycompany.project32;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class FileSystem {

    private BinaryTreeNode<FileSystemItem> root;

    public FileSystem() {
        this.root = new BinaryTreeNode<>(null);  
    }

    public BinaryTreeNode<FileSystemItem> getRoot() {
        return root;
    }

    public void setRoot(BinaryTreeNode<FileSystemItem> root) {
        this.root = root;
    }

    
    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        Stack<Directory> directoryStack = new Stack<>();
        Directory currentDirectory = null;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("\\")) { 
                String dirName = extractDirectoryName(line);
                Directory dir = new Directory(dirName, "USER", "1.1.2024");

                if (currentDirectory != null) {
                    BinaryTreeNode<FileSystemItem> dirNode = new BinaryTreeNode<>(dir);
                    if (currentDirectory.getRoot() == null) {
                        currentDirectory.setRoot(dirNode);
                    } else {
                        BinaryTreeNode<FileSystemItem> currentNode = currentDirectory.getRoot();
                        while (currentNode.getLeft() != null) {
                            currentNode = currentNode.getLeft();
                        }
                        currentNode.setLeft(dirNode);
                    }
                }

                directoryStack.push(currentDirectory);  
                currentDirectory = dir;  
            } else if (containsFileDetails(line)) { 
                File file = extractFileDetails(line);
                if (currentDirectory != null) {
                    BinaryTreeNode<FileSystemItem> fileNode = new BinaryTreeNode<>(file);
                    if (currentDirectory.getRoot() == null) {
                        currentDirectory.setRoot(fileNode);
                    } else {
                        BinaryTreeNode<FileSystemItem> currentNode = currentDirectory.getRoot();
                        while (currentNode.getRight() != null) {
                            currentNode = currentNode.getRight();
                        }
                        currentNode.setRight(fileNode);
                    }
                }
            } else { 
                if (!directoryStack.isEmpty()) {
                    currentDirectory = directoryStack.pop();
                } else {
                    currentDirectory = null;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    private String extractDirectoryName(String line) {
        StringBuilder dirName = new StringBuilder();
        boolean inName = false;
        for (int i = 1; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c != ' ' || inName) {
                dirName.append(c);
                inName = true;
            }
        }
        return dirName.toString();
    }

    private boolean containsFileDetails(String line) {
        int separatorCount = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '#') {
                separatorCount++;
            }
        }
        return separatorCount == 3;
    }

    private File extractFileDetails(String line) {
        int firstHash = line.indexOf('#');
        int secondHash = line.indexOf('#', firstHash + 1);
        int thirdHash = line.indexOf('#', secondHash + 1);

        String fileName = extractFileName(line, 0, firstHash);
        String lastModifiedDate = extractFileName(line, firstHash + 2, secondHash);
        double size = Double.parseDouble(extractFileName(line, secondHash + 2, thirdHash));
        String accessLevel = extractFileName(line, thirdHash + 2, line.length());

        return new File(fileName, fileName.substring(fileName.lastIndexOf('.') + 1), accessLevel, lastModifiedDate, size);
    }

    private String extractFileName(String line, int startIndex, int endIndex) {
        StringBuilder sb = new StringBuilder();
        boolean inName = false;
        for (int i = startIndex; i < endIndex; i++) {
            char c = line.charAt(i);
            if (c != ' ' || inName) {
                sb.append(c);
                inName = true;
            }
        }
        return sb.toString();
    }

    public void addContent(Directory parent, Directory dir) {
        BinaryTreeNode<FileSystemItem> dirNode = new BinaryTreeNode<>(dir);
        if (parent.getRoot() == null) {
            parent.setRoot(dirNode);
        } else {
            BinaryTreeNode<FileSystemItem> currentNode = parent.getRoot();
            while (currentNode.getLeft() != null) {
                currentNode = currentNode.getLeft();
            }
            currentNode.setLeft(dirNode);
        }
    }

    public void addContent(Directory parent, File file) {
        BinaryTreeNode<FileSystemItem> fileNode = new BinaryTreeNode<>(file);
        if (parent.getRoot() == null) {
            parent.setRoot(fileNode);
        } else {
            BinaryTreeNode<FileSystemItem> currentNode = parent.getRoot();
            while (currentNode.getRight() != null) {
                currentNode = currentNode.getRight();
            }
            currentNode.setRight(fileNode);
        }
    }

    public void searchByName(String name) {
        searchByNameHelper(root, name);
    }

    private void searchByNameHelper(BinaryTreeNode<FileSystemItem> node, String name) {
        if (node == null) {
            return;
        }

        if (node.getData().getName().contains(name)) {
            System.out.println("Found: " + node.getData().getName());
        }

        searchByNameHelper(node.getLeft(), name);
        searchByNameHelper(node.getRight(), name);
    }

    public void searchByExtension(String extension) {
        searchByExtensionHelper(root, extension);
    }

    private void searchByExtensionHelper(BinaryTreeNode<FileSystemItem> node, String extension) {
        if (node == null) {
            return;
        }

        if (node.getData() instanceof File && ((File) node.getData()).getExtension().equals(extension)) {
            System.out.println("File: " + node.getData().getName());
        }

        searchByExtensionHelper(node.getLeft(), extension);
        searchByExtensionHelper(node.getRight(), extension);
    }

    public void listContents(Directory dir) {
        listContentsHelper(dir.getRoot());
    }

    private void listContentsHelper(BinaryTreeNode<FileSystemItem> node) {
        if (node == null) {
            return;
        }

        System.out.println(node.getData());
        listContentsHelper(node.getLeft());
        listContentsHelper(node.getRight());
    }

    public void displayPath(FileSystemItem item) {
        System.out.println(getPath(item));
    }

    private String getPath(FileSystemItem item) {
        if (item == null) {
            return "";
        }

        String path = "/" + item.getName();
        if (item instanceof Directory) {
            Directory parentDir = getParentDirectory((Directory) item);  
            if (parentDir != null) {
                path = getPath(parentDir) + path;  
            }
        }
        return path;
    }

    private Directory getParentDirectory(Directory currentDir) {
        return null;  
    }

    public Directory findDirectoryByName1(BinaryTreeNode<FileSystemItem> node, String name) {
        if (node == null) {
            return null;
        }
        
        if (node.getData() instanceof Directory) {
            Directory dir = (Directory) node.getData();
            if (dir.getName().equals(name)) {
                return dir;
            }
        }

        Directory leftSearch = findDirectoryByName1(node.getLeft(), name);
        if (leftSearch != null) {
            return leftSearch;
        }

        return findDirectoryByName1(node.getRight(), name);
    }

    public BinaryTreeNode<FileSystemItem> findDirectoryByName(BinaryTreeNode<FileSystemItem> node, String name) {
        if (node == null) {
            return null;
        }

        if (node.getData() instanceof Directory) {
            Directory dir = (Directory) node.getData();
            if (dir.getName().equals(name)) {
                return node; 
            }
        }

        BinaryTreeNode<FileSystemItem> leftSearch = findDirectoryByName(node.getLeft(), name);
        if (leftSearch != null) {
            return leftSearch;
        }

        return findDirectoryByName(node.getRight(), name);
    }

    public FileSystemItem findNodeByName(BinaryTreeNode<FileSystemItem> node, String name) {
        if (node == null) {
            return null;
        }

        if (node.getData().getName().equals(name)) {
            return node.getData();
        }

        FileSystemItem leftSearch = findNodeByName(node.getLeft(), name);
        if (leftSearch != null) {
            return leftSearch;
        }

        return findNodeByName(node.getRight(), name);
    }
    public void display() {
    displayDirectory(root, 0);
}

private void displayDirectory(BinaryTreeNode<FileSystemItem> node, int level) {
    if (node == null) {
        return;
    }

    for (int i = 0; i < level; i++) {
        System.out.print("    ");
    }

    FileSystemItem item = node.getData();
    if (item instanceof Directory) {
        System.out.println("Directory: " + item.getName());
    } else if (item instanceof File) {
        System.out.println("File: " + item.getName());
    }

    displayDirectory(node.getLeft(), level + 1);
    displayDirectory(node.getRight(), level + 1);
}
}
