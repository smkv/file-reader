package ee.smkv.server;

import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.InputStream;
import java.io.OutputStream;

public class ResourceHttpHandler extends HttpHandler {
  @Override
  public void service(Request request, Response response) throws Exception {
    String resourceUri = request.getRequestURI();
    resourceUri = resourceUri.replaceAll("\\.\\./", ""); // forbid all relative paths '../' in path
    InputStream resourceAsStream = null;
    OutputStream outputStream = null;
    try {
      resourceAsStream = getClass().getResourceAsStream(resourceUri);
      outputStream = response.getOutputStream();
      int read;
      while ((read = resourceAsStream.read()) > 0) {
        outputStream.write(read);
      }
    }
    finally {
      if (resourceAsStream != null) {
        resourceAsStream.close();
      }
      if (outputStream != null) {
        outputStream.close();
      }
    }

  }
}
