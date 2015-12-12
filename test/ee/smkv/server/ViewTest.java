package ee.smkv.server;

import org.junit.Test;

import java.io.StringWriter;

import static org.junit.Assert.*;

public class ViewTest {

    @Test
    public void testRender() throws Exception {
        View view = new View("test.ftl");
        Model model = new Model();
        model.put("var", "JUnitTest");

        StringWriter writer = new StringWriter();
        view.render(model, writer);
        assertEquals("<html><body>JUnitTest</body></html>", writer.toString());
    }
}