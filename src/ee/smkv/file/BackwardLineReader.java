package ee.smkv.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BackwardLineReader implements LineReader {
    private final long startPoint;
    private final int lineCount;

    public BackwardLineReader(long startPoint, int lineCount) {
        this.startPoint = startPoint;
        this.lineCount = lineCount;
    }

    @Override
    public List<Line> read(RandomAccessFile raf) throws IOException {
        final List<Line> lines = new ArrayList<Line>();
        raf.seek(startPoint - 1);
        Buffer buffer = new Buffer();
        long end = startPoint;
        while (lines.size() < lineCount) {
            byte b = raf.readByte();
            if (b == '\n') {
                if (end != raf.getFilePointer()) {
                    lines.add(new Line(raf.getFilePointer(), end, buffer.reverse().toString()));
                    buffer.reset();
                    end = raf.getFilePointer();
                }
            } else {
                buffer.append(b);
            }

            long next = raf.getFilePointer() - 2;
            raf.seek(Math.max(0, next));
            if (next < 0) {
                break;
            }

        }
        if (!buffer.isEmpty()) {
            lines.add(new Line(raf.getFilePointer(), end, buffer.reverse().toString()));
        }
        Collections.reverse(lines);
        return lines;
    }
}
