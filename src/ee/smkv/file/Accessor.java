package ee.smkv.file;

import java.io.IOException;
import java.io.RandomAccessFile;

interface Accessor {
  void access(RandomAccessFile randomAccessFile) throws IOException;
}
