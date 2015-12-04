package ee.smkv.file;

import java.io.UnsupportedEncodingException;

class Buffer {
  public static final int DELTA = 1024;
  protected byte[] array;
  protected int cursor = -1;

  Buffer() {
    this(DELTA);
  }

  Buffer(int capacity) {
    array = new byte[capacity];
  }

  void append(byte b) {
    incrementCursorPosition();
    array[cursor] = b;
  }

  private void incrementCursorPosition() {
    cursor++;
    if (cursor >= array.length) {
      increaseArray(DELTA);
    }
  }

  private void increaseArray(int delta) {
    byte[] temp = new byte[array.length + delta];
    System.arraycopy(array, 0, temp, 0, array.length);
    array = temp;
  }

  byte[] array() {
    byte[] temp = new byte[size()];
    System.arraycopy(array, 0, temp, 0, temp.length);
    return temp;
  }

  void reset() {
    cursor = -1;
  }

  int size() {
    return cursor + 1;
  }

  boolean isEmpty() {
    return size() == 0;
  }

  Buffer reverse() {
    Buffer buffer = new Buffer(size());
    for (int i = cursor; i >= 0; i--) {
      buffer.append(array[i]);
    }
    return buffer;
  }

  public String toString() {
    return isEmpty() ? "" : new String(array, 0, size());
  }

  public String toString(String charset) throws UnsupportedEncodingException {
    return isEmpty() ? "" : new String(array, 0, size(), charset);
  }


}
