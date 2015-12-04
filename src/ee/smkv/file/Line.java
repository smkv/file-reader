package ee.smkv.file;

public class Line {
  private final long startPoint;
  private final long endPoint;
  private final String content;

  Line(long startPoint, long endPoint, String content) {
    this.startPoint = startPoint;
    this.endPoint = endPoint;
    this.content = content;
  }

  public long getStartPoint() {
    return startPoint;
  }

  public long getEndPoint() {
    return endPoint;
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return String.format("[%d:%d] %s", startPoint, endPoint, content);
  }
}
