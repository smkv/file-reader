package ee.smkv.file;

import org.junit.Test;

import static org.junit.Assert.*;

public class BufferTest {

  @Test
  public void testAppendAndArray() throws Exception {
    Buffer buffer = new Buffer();
    assertArrayEquals(new byte[0], buffer.array());

    buffer.append((byte)0);
    assertArrayEquals(new byte[]{0}, buffer.array());

    buffer.append((byte)1);
    assertArrayEquals(new byte[]{0, 1}, buffer.array());

    buffer.append((byte)2);
    assertArrayEquals(new byte[]{0, 1, 2}, buffer.array());
  }


  @Test
  public void testReset() throws Exception {

    Buffer buffer = new Buffer();
    assertArrayEquals(new byte[0], buffer.array());
    buffer.append((byte)1);
    buffer.append((byte)2);
    buffer.append((byte)3);
    buffer.reset();
    assertArrayEquals(new byte[0], buffer.array());
  }

  @Test
  public void testReverse() throws Exception {

    Buffer buffer = new Buffer();
    buffer.append((byte)1);
    buffer.append((byte)2);
    buffer.append((byte)3);
    assertArrayEquals(new byte[]{1, 2, 3}, buffer.array());
    assertArrayEquals(new byte[]{3, 2, 1}, buffer.reverse().array());
  }

  @Test
  public void testToString() throws Exception {

  }
}