package ee.smkv.server;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

class RedirectHttpHandler extends HttpHandler {
    private static final Logger log = Logger.getLogger(RedirectHttpHandler.class);
    private final String location;

    public RedirectHttpHandler(String location) {
        this.location = location;
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        log.info(String.format("%s %s > %s", request.getMethod(), request.getRequestURI(), location));
        response.sendRedirect(location);
    }
}
