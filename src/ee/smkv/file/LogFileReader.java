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

    public List<Line> read(final long startPoint, final int lineCount) throws IOException {
        final List<Line> lines = new ArrayList<Line>();
        read(new Accessor() {
            @Override
            public void access(RandomAccessFile randomAccessFile) throws IOException {
                readLines(randomAccessFile, startPoint, lines, lineCount);
            }
        });
        return lines;
    }

    public List<Line> readUp(final long startPoint, final int lineCount) throws IOException {
        final List<Line> lines = new ArrayList<Line>();
        read(new Accessor() {
            @Override
            public void access(RandomAccessFile randomAccessFile) throws IOException {
                int foundLines = 0;
                long cursor = startPoint-1;
                while (foundLines <= lineCount && cursor > 0) {
                    cursor--;
                    randomAccessFile.seek(cursor);
                    if (randomAccessFile.read() == '\n') {
                        foundLines++;
                    }
                }
                readLines(randomAccessFile, cursor, lines, foundLines);
            }
        });
        return lines;
    }

    private void readLines(RandomAccessFile randomAccessFile, long cursor, List<Line> lines, int lineCount) throws IOException {
        while (lines.size() < lineCount && cursor < file.length()) {
            randomAccessFile.seek(cursor);
            String line = randomAccessFile.readLine();
            lines.add(new Line(cursor, randomAccessFile.getFilePointer(), line));
            cursor = randomAccessFile.getFilePointer();
        }
    }

    private void read(Accessor accessor) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            accessor.access(randomAccessFile);
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
    }


    private interface Accessor {
        void access(RandomAccessFile randomAccessFile) throws IOException;
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

        @Override
        public String toString() {
            return String.format("[%d:%d] %s", startPoint, endPoint, content);
        }
    }
}
