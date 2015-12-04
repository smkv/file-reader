package ee.smkv.server;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public class View {
  private final String name;

  private static Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
  static {
    cfg.setClassForTemplateLoading(View.class , "/views");
  }

  public View(String name) {
    this.name = name;
  }

  public void render(Writer writer , Model model) throws IOException, TemplateException {
    Template template = cfg.getTemplate(name);
    template.process(model, writer);
  }

}
