package ee.smkv.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class LogFileReader {
    private final File file;

    public LogFileReader(File file) {
        this.file = file;
    }

    public List<Line> read(long startPoint, int lineCount) throws IOException {
        List<Line> lines = new ArrayList<Line>();

        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");

            while (lines.size() < lineCount && startPoint < file.length()) {
                randomAccessFile.seek(startPoint);
                String line = randomAccessFile.readLine();
                lines.add(new Line(startPoint, randomAccessFile.getFilePointer(), line));
                startPoint = randomAccessFile.getFilePointer();
            }


        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }

        return lines;
    }

    public class Line {
        private final long startPoint;
        private final long endPoint;
        private final String content;

        public Line(long startPoint, long endPoint, String content) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.content = content;
        }

        public long getStartPoint() {
            return startPoint;
        }

        public long getEndPoint() {
            return endPoint;
        }

        public String getContent() {
            return content;
        }
    }
}
