package com.hazebyte.utils.file;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class FileHelper {

    private FileHelper() {}

    public static Set<File> getFiles(File directory) {
        return getFiles(directory, null);
    }

    /**
     * Performs a BFS on a directory and return all files that satisfy the fileFilter.
     */
    public static Set<File> getFiles(File directory, FileFilter fileFilter) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Input directory does not exist or is not a dir.");
        }

        Set<File> files = new HashSet();
        Queue<File> queue = new LinkedList<>();
        queue.add(directory);
        while (!queue.isEmpty()) {
            File current = queue.poll();

            if (!current.isDirectory()) {
                files.add(current);
                continue;
            }

            File[] listDir = fileFilter != null ? current.listFiles(fileFilter) : current.listFiles();
            for (File file: listDir) {
                queue.add(file);
            }
        }
        return files;
    }
}
