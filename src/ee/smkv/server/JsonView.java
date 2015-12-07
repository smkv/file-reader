package ee.smkv.server;

import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.Response;

import java.io.IOException;

public class JsonView {
    private Gson gson = new Gson();
    public void render(Object object , Response response) throws IOException {
        String json = gson.toJson(object);
        response.setHeader("Content-Type", "text/json; charset=utf-8");
        response.getWriter().write(json);
    }
}
