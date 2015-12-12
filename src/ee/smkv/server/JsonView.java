package ee.smkv.server;

import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.grizzly.http.util.ContentType;

import java.io.IOException;
import java.io.Writer;

public class JsonView {
    private Gson gson = new Gson();
    public void render(Object object , Response response) throws IOException {
        response.setContentType(ContentType.newContentType("text/json" ,"utf-8"));
        render(object , response.getWriter());
    }

    public void render(Object object , Writer writer) throws IOException {
        writer.write(gson.toJson(object));
    }
}
