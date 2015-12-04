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
  public void testSize() throws Exception {
    Buffer buffer = new Buffer();
    assertEquals(0 , buffer.size());
    buffer.append((byte)1);
    assertEquals(1 , buffer.size());

    buffer.append((byte)1);
    assertEquals(2 , buffer.size());

  }
  @Test
  public void testIsEmpty() throws Exception {
    Buffer buffer = new Buffer();
    assertTrue(buffer.isEmpty());
    buffer.append((byte)46);
    assertFalse(buffer.isEmpty());

  }

  @Test
  public void testToString() throws Exception {
    Buffer buffer = new Buffer();
    assertEquals("" , buffer.toString());
    buffer.append((byte)46);
    assertEquals("." , buffer.toString());
  }

  @Test
  public void testToStringUft8() throws Exception {
    Buffer buffer = new Buffer();

    for (byte b : "asdfghkфывапролдж".getBytes("UTF8")) {
      buffer.append(b);
    }

    assertEquals("asdfghkфывапролдж" , buffer.toString("UTF8"));
  }

  @Test
  public void testIncreaseBufferSize() throws Exception {
    Buffer buffer = new Buffer(2);
    assertEquals(2 , buffer.array.length);

    buffer.append((byte)46);
    assertEquals(2 , buffer.array.length);

    buffer.append((byte)46);
    assertEquals(2 , buffer.array.length);

    buffer.append((byte)46);
    assertEquals(1026 , buffer.array.length);

  }
}