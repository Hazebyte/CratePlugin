package com.hazebyte.utils.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FileHelperTest {

    Path path1, path2, path3, path4;
    File file1, file2, file3, file4;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setup() throws IOException {
        path1 = tempDir.resolve("test1.txt");
        path2 = tempDir.resolve("test2.txt");
        path3 = tempDir.resolve("n1/test3.txt");
        path4 = tempDir.resolve("n1/n2/n3/test4.yml");

        file1 = path1.toFile();
        file2 = path2.toFile();
        file3 = path3.toFile();
        file4 = path4.toFile();
        file1.createNewFile();
        file2.createNewFile();
        file3.getParentFile().mkdirs(); file3.createNewFile();
        file4.getParentFile().mkdirs(); file4.createNewFile();
    }

    @Nested
    public class getFiles {

        @Test
        public void shouldGetAllFiles() {
            Set<File> files = FileHelper.getFiles(file1.getParentFile());
            assertEquals(4, files.size());
            assertTrue(files.contains(file1));
            assertTrue(files.contains(file2));
            assertTrue(files.contains(file3));
            assertTrue(files.contains(file4));
        }

        @Test
        public void shouldGetTextFiles() {
            FileFilter filter = file -> file.isDirectory() || file.getName().endsWith(".txt");
            Set<File> files = FileHelper.getFiles(file1.getParentFile(), filter);
            assertEquals(3, files.size());
            assertTrue(files.contains(file1));
            assertTrue(files.contains(file2));
            assertTrue(files.contains(file3));
        }

        @Test
        public void shouldGetYamlFiles() {
            FileFilter filter = file -> file.isDirectory() || file.getName().endsWith(".yml");
            Set<File> files = FileHelper.getFiles(file1.getParentFile(), filter);
            assertEquals(1, files.size());
            assertTrue(files.contains(file4));
        }

    }
}
