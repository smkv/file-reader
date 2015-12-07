package ee.smkv.server;

import java.io.File;
import java.io.FileFilter;

class LogFileFilter implements FileFilter {
    @Override
    public boolean accept(File file) {
        return !file.getName().startsWith(".") && (file.isDirectory() || file.getName().toLowerCase().endsWith(".log"));
    }
}
