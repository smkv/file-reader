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
      public void access(RandomAccessFile randomAccessFile) throws IOException {
        long cursor = startPoint;
        while (lines.size() < lineCount && cursor < file.length()) {
          randomAccessFile.seek(cursor);
          String line = randomAccessFile.readLine();
          lines.add(new Line(cursor, randomAccessFile.getFilePointer(), line));
          cursor = randomAccessFile.getFilePointer();
        }
      }
    });
    return lines;
  }

  public List<Line> readUp(final long startPoint, final int lineCount) throws IOException {
    final List<Line> lines = new ArrayList<Line>();
    if(startPoint <=0) return lines;
    read(new Accessor() {
      @Override
      public void access(RandomAccessFile raf) throws IOException {
        raf.seek(startPoint-1);
        Buffer buffer = new Buffer();
        long end = startPoint;
        while (lines.size() < lineCount && raf.getFilePointer() > 0) {
          byte b = raf.readByte();
          if (b == '\n' && end != raf.getFilePointer()) {
            lines.add(new Line(raf.getFilePointer(), end, buffer.reverse().toString()));
            buffer.reset();
            end = raf.getFilePointer();
          }else{
            buffer.append(b);
          }

          shiftPointer(raf, -2);
        }
        if (!buffer.isEmpty()) {
          lines.add(new Line(raf.getFilePointer(), end, buffer.reverse().toString()));
        }
      }

      private void shiftPointer(RandomAccessFile raf, int delta) throws IOException {
        long next = raf.getFilePointer() + delta;
        raf.seek(Math.max(0, next));
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
