package com.adventofcode.calendar.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.adventofcode.calendar.Puzzle;

public class Day7 implements Puzzle {

    private static final String COMMAND_CD_ROOT = "/";
    private static final String COMMAND_CD_PARENT = "..";

    private static final String DIR = "dir";
    private static final String COMMAND_CHAR = "$";
    private static final String COMMAND_CD = "cd";
    private static final int MAX_DIR_SIZE = 100000;
    private static final int REQUIRED_SPACE = 30000000;
    private static final int DISK_SIZE = 70000000;

    @Override
    public String partOne(String input) {
        File file = buildTree(input.split(System.lineSeparator()));
        int sum = sumSizesOfFoldersBelowThreshold(file.getRoot(), MAX_DIR_SIZE);
        return "Sum of folder sizes below threshold: " + sum;
    }

    @Override
    public String partTwo(String input) {
        File root = buildTree(input.split(System.lineSeparator())).getRoot();
        int usedSpace = root.getSize();
        int threshold = REQUIRED_SPACE - (DISK_SIZE - usedSpace);
        int minSizeRequired = getFolderSizeOfAtLeastThreshold(root, threshold, Integer.MAX_VALUE);
        return "Size freed after deleting folder: " + minSizeRequired;
    }

    private File buildTree(String[] lines) {
        File file = new File();
        for (int i = 0; i < lines.length; i++) {
            String[] command = lines[i].split(" ");
            switch (command[0]) {
                case COMMAND_CHAR:
                    file = handleCommand(command, file);
                    break;
                case DIR:
                    file.addChild(command[1]);
                    break;
                default:
                    file.addChild(Integer.valueOf(command[0]), command[1]);
            }
        }
        return file;
    }

    private File handleCommand(String[] command, File file) {
        switch (command[1]) {
            case COMMAND_CD:
                switch (command[2]) {
                    case COMMAND_CD_ROOT:
                        return file.getRoot();
                    case COMMAND_CD_PARENT:
                        return file.getParent();
                    default:
                        return file.getChild(command[2]);
                }
            default:
                return file;
        }
    }

    private int sumSizesOfFoldersBelowThreshold(File file, int threshold) {
        int size = file.getSize();
        int sum = size <= threshold ? size : 0;

        for (File child : file.getChildren()) {
            if (child.isFolder()) {
                sum += sumSizesOfFoldersBelowThreshold(child, threshold);
            }
        }
        return sum;
    }

    private int getFolderSizeOfAtLeastThreshold(File file, int threshold, int minSizeRequired) {
        int size = file.getSize();
        minSizeRequired = size >= threshold ? Math.min(size, minSizeRequired) : minSizeRequired;

        for (File child : file.getChildren()) {
            if (child.isFolder()) {
                minSizeRequired = Math.min(minSizeRequired,
                        getFolderSizeOfAtLeastThreshold(child, threshold, minSizeRequired));
            }
        }
        return minSizeRequired;
    }

    private class File {
        private final File parent;
        private final List<File> children = new ArrayList<File>();
        private final Integer size;
        private final String name;
        private final boolean isFolder;

        File() {
            this(null, null);
        }

        File(File parent, int size, String name) {
            this.size = size;
            this.name = name;
            this.parent = parent;
            this.isFolder = false;
        }

        public File(File parent, String name) {
            this.name = name;
            this.size = null;
            this.parent = parent;
            this.isFolder = true;
        }

        void addChild(int size, String name) {
            children.add(new File(this, size, name));
        }

        void addChild(String name) {
            children.add(new File(this, name));
        }

        boolean isFolder() {
            return isFolder;
        }

        File getChild(final String name) {
            return children.stream().filter(n -> name.equals(n.getName())).findAny().orElse(null);
        }

        int getSize() {
            return isFolder() ? children.stream().mapToInt(File::getSize).sum() : size;
        }

        List<File> getChildren() {
            return Collections.unmodifiableList(children);
        }

        String getName() {
            return name;
        }

        File getRoot() {
            return parent == null ? this : parent.getRoot();
        }

        public File getParent() {
            return parent;
        }
    }
}