package ee.smkv.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

class ForwardLineReader implements LineReader {
    private final long startPoint;
    private final int lineCount;
    private final long fileSize;

    public ForwardLineReader(long startPoint, int lineCount, long fileSize) {
        this.fileSize = fileSize;
        this.startPoint = startPoint;
        this.lineCount = lineCount;
    }

    @Override
    public List<Line> read(RandomAccessFile raf) throws IOException {
        List<Line> lines = new ArrayList<Line>();
        long start = startPoint;
        raf.seek(start);
        Buffer buffer = new Buffer();
        while (lines.size() < lineCount && raf.getFilePointer() < fileSize) {
            byte b = raf.readByte();

            if (b == '\n') {
                lines.add(new Line(start, raf.getFilePointer(), buffer.toString()));
                buffer.reset();
                start = raf.getFilePointer();
            } else {
                buffer.append(b);
            }
        }

        if (!buffer.isEmpty()) {
            lines.add(new Line(start, raf.getFilePointer(), buffer.toString()));
        }
        return lines;
    }
}
