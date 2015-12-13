package ee.smkv.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Collections;
import java.util.List;

public class LogFileReader {
    private final File file;

    public LogFileReader(File file) {
        this.file = file;
    }

    public List<Line> readForward(long startPoint, int lineCount) throws IOException {
        startPoint = Math.max(startPoint, 0);
        return read(new ForwardLineReader(startPoint, lineCount, file.length()));
    }

    public List<Line> readBackward(long startPoint, int lineCount) throws IOException {
        if (startPoint <= 0) {
            return Collections.emptyList();
        }
        startPoint = Math.min(startPoint, file.length());
        return read(new BackwardLineReader(startPoint, lineCount));
    }


    private List<Line> read(LineReader reader) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            return reader.read(randomAccessFile);
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
    }


}
