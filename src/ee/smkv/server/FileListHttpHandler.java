package ee.smkv.server;

import ee.smkv.file.FilePagination;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class FileListHttpHandler extends HttpHandler {

  public static final FileFilter FILE_FILTER = new FileFilter() {
    @Override
    public boolean accept(File file) {
      return !file.getName().startsWith(".") && (file.isDirectory() || file.getName().toLowerCase().endsWith(".log"));
    }
  };
  private final File root;

  public FileListHttpHandler(File root) {
    this.root = root.getAbsoluteFile();
  }

  public FileListHttpHandler(String root) {
    this.root = new File(root).getAbsoluteFile();
  }

  @Override
  public void service(Request request, Response response) throws Exception {

    String directory = request.getRequestURI().substring(request.getContextPath().length());
    directory = directory.replaceAll("\\.\\./", ""); // forbid all relative paths '../' in path
    File file = new File(root, directory);

    if (!FILE_FILTER.accept(file)) {
      file = root;
    }

    if (file.isFile()) {
      FilePagination pagination = new FilePagination(file, 1024 * 10);// default page 10kB
      if (isAjax(request)) {
        int page = request.getParameter("page") != null ? Integer.valueOf(request.getParameter("page")) : 0;
        response.setHeader("Content-Type" , "text/plain; charset=utf-8");
        response.getOutputStream().write(pagination.getPage(page));
        response.getOutputStream().close();
        return;
      }

      directory = directory.substring(0, directory.length() - file.getName().length());
      View view = new View("file.ftl");
      Model model = new Model();
      model.put("directory", directory);
      int pageNumber = request.getParameter("startPage") != null ? Integer.valueOf(request.getParameter("startPage")) : 0;
      model.put("startPage",pageNumber);
      model.put("file", file);
      view.render(response.getWriter(), model);

    }
    else {
      View view = new View("files.ftl");
      Model model = new Model();
      model.put("directory", directory);
      File[] listFiles = file.listFiles(FILE_FILTER);
      Arrays.sort(listFiles);
      model.put("files", listFiles);
      view.render(response.getWriter(), model);
    }
  }


  private boolean isAjax(Request request) {
    return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
  }


}
