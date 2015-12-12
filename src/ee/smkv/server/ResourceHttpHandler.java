package ee.smkv.server;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.ContentType;
import org.glassfish.grizzly.http.util.MimeType;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ResourceHttpHandler extends HttpHandler {
    private static final Logger log = Logger.getLogger(ResourceHttpHandler.class);

    static {
        MimeType.add("ttf","application/font");
        MimeType.add("eot","application/font-eot");
        MimeType.add("woff","application/font-woff");
        MimeType.add("woff2","application/font-woff2");
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        log.info(String.format("%s %s", request.getMethod(), request.getRequestURI()));
        String resourceUri = request.getRequestURI();
        resourceUri = resourceUri.replaceAll("\\.\\./", ""); // forbid all relative paths '../' in path
        InputStream resourceAsStream = null;

        try {
            resourceAsStream = getClass().getResourceAsStream(resourceUri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int read;
            while ((read = resourceAsStream.read()) >= 0) {
                outputStream.write(read);
            }

            byte[] bytes = outputStream.toByteArray();
            response.setContentType(getContentType(resourceUri));
            response.setContentLength(bytes.length);
            response.getOutputStream().write(bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
        }

    }

    private ContentType getContentType(String resourceUri) {
        String mimeType = MimeType.getByFilename(resourceUri);
        return ContentType.newContentType(mimeType);
    }


}
