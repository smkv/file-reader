package ee.smkv.server;

import org.glassfish.grizzly.http.server.Response;
import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.*;

public class JsonViewTest {

    @Test
    public void testRender() throws Exception {
        JsonView view = new JsonView();
        StringWriter writer = new StringWriter();
        Model model = new Model();

        model.put("first" , 1);
        model.put("second" , "2");
        model.put("third" , "three");

        view.render(model , writer);

        assertEquals("{\"first\":1,\"second\":\"2\",\"third\":\"three\"}" , writer.toString());
    }

}