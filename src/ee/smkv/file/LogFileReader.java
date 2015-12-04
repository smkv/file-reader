package ee.smkv.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
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
      public void access(RandomAccessFile raf) throws IOException {
        long start = startPoint;
        raf.seek(start);
        Buffer buffer = new Buffer();
        while (lines.size() < lineCount && raf.getFilePointer() < file.length()) {
          byte b = raf.readByte();

          if (b == '\n') {
            lines.add(new Line(start, raf.getFilePointer(), buffer.toString()));
            buffer.reset();
            start = raf.getFilePointer();
          }
          else {
            buffer.append(b);
          }
        }

        if (!buffer.isEmpty()) {
          lines.add(new Line(start, raf.getFilePointer(), buffer.toString()));
        }
      }
    });
    return lines;
  }

  public List<Line> readUp(final long startPoint, final int lineCount) throws IOException {
    final List<Line> lines = new ArrayList<Line>();
    if (startPoint <= 0) {
      return lines;
    }
    read(new Accessor() {
      @Override
      public void access(RandomAccessFile raf) throws IOException {
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
          }
          else {
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
      }
    });

    Collections.reverse(lines);
    return lines;
  }


  private void read(Accessor accessor) throws IOException {
    RandomAccessFile randomAccessFile = null;
    try {
      randomAccessFile = new RandomAccessFile(file, "r");
      accessor.access(randomAccessFile);
    }
    finally {
      if (randomAccessFile != null) {
        randomAccessFile.close();
      }
    }
  }


}
