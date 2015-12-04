package ee.smkv.file;

import java.io.UnsupportedEncodingException;

class Buffer {
  private byte[] array = new byte[1024];
  private int cursor = -1;

  void append(byte b) {
    incrementCursorPosition();
    array[cursor] = b;
  }

  private void incrementCursorPosition() {
    cursor++;
    if (cursor >= array.length) {
      increaseArray(1024);
    }
  }

  private void increaseArray(int delta) {
    byte[] temp = new byte[array.length + delta];
    System.arraycopy(array, 0, temp, 0, array.length);
    array = temp;
  }

  byte[] array() {
    byte[] temp = new byte[cursor + 1];
    System.arraycopy(array, 0, temp, 0, temp.length);
    return temp;
  }

  void reset() {
    cursor = -1;
  }

  Buffer reverse() {
    Buffer buffer = new Buffer();
    for (int i = cursor; i >= 0; i--) {
      buffer.append(array[i]);
    }
    return buffer;
  }

  public String toString() {
    return new String(array, 0, cursor);
  }

  public String toString(String charset) throws UnsupportedEncodingException {
    return new String(array, 0, cursor, charset);
  }


}
