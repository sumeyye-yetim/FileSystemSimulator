FileSystemSimulator

A Java-based file system simulator that models a hierarchical file system using tree data structures. It reads an initial structure from myfiles.txt and supports directory and file management operations with user and system access levels.

Features

Load initial structure: Parse myfiles.txt to build Directory and File nodes in a tree.

Add Directory: Create a new directory under any existing directory with "USER" access.

Add File: Create a new file (e.g., .txt, .pdf, .png, .mp3) under a directory with "USER" access.

Delete Directory: Remove a directory and its contents if all are "USER" access.

Delete File: Remove a file with "USER" access and update parent directory sizes and dates.

Search by Name: Find all files or directories matching a given name within a specified subtree.

Search by Extension: Find all files with a given extension under a specified directory.

Display Path: Print the full path from the root to any file or directory.

List Contents: Show details (name, size, date, access) of all immediate children in a directory with "USER" access.

Access Levels: Each file or directory maintains an access level ("USER" or "SYSTEM"); directories inherit the highest-level rules of their contents.

Size & Date Management: Directories automatically update their size (sum of contents) and last modification date (latest of contents).

File Structure

project-root/
├── src/                         # Java source files
│   ├── FileSystemItem.java      # Abstract base class for files and directories
│   ├── File.java                # File node implementation
│   ├── Directory.java           # Directory node implementation
│   ├── BinaryTreeNode.java      # Generic tree node for directory contents
│   ├── FileSystem.java          # Core logic for file system operations
│   ├── Main.java                # Console menu and user interaction
├── myfiles.txt                  # Initial file system snapshot
└── README.md                    # Project documentation

Requirements

Java SE Development Kit (JDK) 8 or higher

Contributing

Contributions are welcome! Please fork the repo and submit pull requests for bug fixes or additional features.

Author

Sümeyye Yetim


