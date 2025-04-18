
package com.mycompany.project32;

public class BinaryTreeNode<FileSystemItem> {
    private FileSystemItem data;
    private BinaryTreeNode<FileSystemItem> left;
    private BinaryTreeNode<FileSystemItem> right;

    public BinaryTreeNode(FileSystemItem data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public FileSystemItem getData() {
        return data;
    }
    
     public void setData(FileSystemItem data) {
        this.data = data;
    }

    public BinaryTreeNode<FileSystemItem> getLeft() {
        return left;
    }

    public void setLeft(BinaryTreeNode<FileSystemItem> left) {
        this.left = left;
    }

    public BinaryTreeNode<FileSystemItem> getRight() {
        return right;
    }

    public void setRight(BinaryTreeNode<FileSystemItem> right) {
        this.right = right;
    }
}
