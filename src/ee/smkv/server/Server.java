package ee.smkv.server;

import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

public class Server {

  public static void main(String[] args) throws IOException, InterruptedException {
    String path = args.length > 0 ? args[0] : ".";
    HttpServer httpServer = HttpServer.createSimpleServer("/", Integer.valueOf(System.getProperty("port", "8080")));
    httpServer.getServerConfiguration().addHttpHandler(new FileListHttpHandler(path), "/files");
    httpServer.getServerConfiguration().addHttpHandler(new ResourceHttpHandler(), "/resources");
    httpServer.start();
    Thread.currentThread().join();
  }
}
