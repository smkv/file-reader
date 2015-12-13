package ee.smkv.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public interface LineReader {
    List<Line> read(RandomAccessFile raf) throws IOException;
}
