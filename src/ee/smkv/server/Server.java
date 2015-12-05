package ee.smkv.server;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        String path = args.length > 0 ? args[0] : ".";
        Integer port = Integer.valueOf(System.getProperty("port", "8080"));

        Logger log = Logger.getLogger(Server.class);
        log.info("Starting http server on port " + port + " to directory" + path);
        HttpServer httpServer = HttpServer.createSimpleServer("/", port);
        httpServer.getServerConfiguration().addHttpHandler(new FileListHttpHandler(path), "/files");
        httpServer.getServerConfiguration().addHttpHandler(new ResourceHttpHandler(), "/resources");
        httpServer.start();
        log.info("Server ready");
        Thread.currentThread().join();
        log.info("Server stopped");
    }
}
