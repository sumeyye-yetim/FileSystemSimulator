package com.mycompany.project32;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);

        String filePath = "/Users/sumeyyeyetim/Desktop/myfiles.txt"; 

        System.out.println("Loading file system from '" + filePath + "'...");
        fileSystem.loadFromFile(filePath); 
        System.out.println("File system loaded successfully.");

        while (true) {
            System.out.println("\nFile System Manager");
            System.out.println("1. Add Directory");
            System.out.println("2. Add File");
            System.out.println("3. Delete Directory");
            System.out.println("4. Delete File");
            System.out.println("5. Search by Name");
            System.out.println("6. Search by Extension");
            System.out.println("7. Display Path");
            System.out.println("8. List Contents");
            System.out.println("9. Display Entire File System");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    // Add Directory
                    System.out.print("Enter the parent directory name: ");
                    String parentDirName = scanner.nextLine().trim();
                    Directory parentDir = fileSystem.findDirectoryByName1(fileSystem.getRoot(), parentDirName);

                    if (parentDir != null && parentDir.getAccessLevel().equalsIgnoreCase("USER")) {
                        System.out.print("Enter the name of the new directory: ");
                        String newDirName = scanner.nextLine().trim(); 

                        Directory newDir = new Directory(newDirName, "USER", "2024-12-01"); 
                        fileSystem.addContent(parentDir, newDir);
                        System.out.println("Directory '" + newDirName + "' added to '" + parentDirName + "'.");
                    } else {
                        System.out.println("Parent directory not found or does not have USER access.");
                    }
                    break;

                case 2:
                    // Add File
                    System.out.print("Enter the parent directory name: ");
                    String parentForFileName = scanner.nextLine();
                    System.out.print("Enter the file name: ");
                    String fileName = scanner.nextLine();
                    System.out.print("Enter the file extension: ");
                    String extension = scanner.nextLine();
                    System.out.print("Enter the file size (in bytes): ");
                    double size = scanner.nextDouble();  
                    scanner.nextLine(); 
                    System.out.print("Enter the access level (USER/SYSTEM): ");
                    String accessLevel = scanner.nextLine();
                    System.out.print("Enter the last modified date (dd.MM.yyyy): ");
                    String date = scanner.nextLine();

                    Directory parentFileDir = fileSystem.findDirectoryByName1(fileSystem.getRoot(), parentForFileName);
                    if (parentFileDir != null && parentFileDir.getAccessLevel().equals("USER")) {
                        File newFile = new File(fileName, extension, accessLevel, date, size);
                        fileSystem.addContent(parentFileDir, newFile);
                    } else {
                        System.out.println("Parent directory not found or does not have USER access.");
                    }
                    break;
                case 3:
                    // Delete Directory
                    System.out.print("Enter the name of the parent directory: ");
                    String deleteParentDirName = scanner.nextLine();
                    System.out.print("Enter the name of the directory to delete: ");
                    String directoryToDeleteName = scanner.nextLine();

                    if (fileSystem.getRoot() == null) {
                        System.out.println("Root directory is null. Please initialize the file system properly.");
                    } else {
                        BinaryTreeNode<FileSystemItem> parentDirectoryNode = fileSystem.findDirectoryByName(fileSystem.getRoot(), deleteParentDirName);

                        if (parentDirectoryNode == null) {
                            System.out.println("Parent directory not found.");
                        } else {
                            FileSystemItem item = parentDirectoryNode.getData();
                            if (item instanceof Directory) {
                                Directory parentDirectory = (Directory) item; 
                                BinaryTreeNode<FileSystemItem> directoryToDeleteNode = fileSystem.findDirectoryByName(parentDirectoryNode, directoryToDeleteName);

                                if (directoryToDeleteNode == null) {
                                    System.out.println("Directory to delete not found.");
                                } else {
                                    FileSystemItem deleteItem = directoryToDeleteNode.getData();
                                    if (deleteItem instanceof Directory) {
                                        Directory directoryToDelete = (Directory) deleteItem; // Type casting

                                        if (directoryToDelete.getAccessLevel().equals("SYSTEM")) {
                                            System.out.println("Cannot delete a directory with SYSTEM access level.");
                                        } else {
                                            int initialContentCount = parentDirectory.getContents().size();
                                            parentDirectory.removeContent(directoryToDelete.getName());
                                            int finalContentCount = parentDirectory.getContents().size();

                                            if (finalContentCount < initialContentCount) {
                                                System.out.println("Directory '" + directoryToDeleteName + "' deleted successfully.");
                                            } else {
                                                System.out.println("Failed to delete the directory. It may not exist.");
                                            }
                                        }
                                    } else {
                                        System.out.println("The item to delete is not a directory.");
                                    }
                                }
                            } else {
                                System.out.println("The parent directory is not of type Directory.");
                            }
                        }
                    }
                    break;
                case 4:
                    // Delete File
                    System.out.print("Enter the parent directory name: ");
                    String deleteParentFileName = scanner.nextLine();
                    System.out.print("Enter the name of the file to delete: ");
                    String deleteFileName = scanner.nextLine();

                    Directory deleteParentFile = fileSystem.findDirectoryByName1(fileSystem.getRoot(), deleteParentFileName);

                    if (deleteParentFile == null) {
                        System.out.println("Parent directory not found.");
                    } else {
                        int initialContentCount = deleteParentFile.getContents().size();
                        deleteParentFile.removeContent(deleteFileName);
                        int finalContentCount = deleteParentFile.getContents().size();

                        if (finalContentCount < initialContentCount) {
                            System.out.println("File '" + deleteFileName + "' deleted successfully.");
                        } else {
                            System.out.println("Failed to delete the file. It may not exist.");
                        }
                    }
                    break;

                case 5:
                    // Search by Name
                    System.out.print("Enter the name to search: ");
                    String searchName = scanner.nextLine();
                    fileSystem.searchByName(searchName);
                    break;

                case 6:
                    // Search by Extension
                    System.out.print("Enter the file extension to search (e.g., txt, pdf): ");
                    String searchExtension = scanner.nextLine();
                    fileSystem.searchByExtension(searchExtension);
                    break;

                case 7:
                    // Display Path
                    System.out.print("Enter the file or directory name to display path: ");
                    String pathName = scanner.nextLine();

                    if (pathName == null || pathName.trim().isEmpty()) {
                        System.out.println("Invalid name entered.");
                        break;
                    }
                    BinaryTreeNode<FileSystemItem> rootNode = fileSystem.getRoot(); 
                    FileSystemItem node = fileSystem.findNodeByName(rootNode, pathName); 

                    if (node != null) {
                        System.out.println("Path: " + node.displayPath());
                    } else {
                        System.out.println("File or directory not found.");
                    }
                    break;

                case 8:
                    // List Contents
                    System.out.print("Enter the directory name to list contents: ");
                    String listDirName = scanner.nextLine();
                    Directory listDir = fileSystem.findDirectoryByName1(fileSystem.getRoot(), listDirName);
                    if (listDir != null && listDir.getAccessLevel().equals("USER")) {
                        fileSystem.listContents(listDir);
                    } else {
                        System.out.println("Directory not found or does not have USER access.");
                    }
                    break;

                case 9:
                    // Display Entire File System
                    fileSystem.display();
                    break;

                case 0:
                    // Exit
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
