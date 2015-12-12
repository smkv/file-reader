package ee.smkv.server;

import ee.smkv.file.FilePagination;
import ee.smkv.file.Line;
import ee.smkv.file.LogFileReader;
import freemarker.template.TemplateException;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileListHttpHandler extends HttpHandler {
    private static final Logger log = Logger.getLogger(FileListHttpHandler.class);

    public static final FileFilter FILE_FILTER = new LogFileFilter();
    private final File root;

    public FileListHttpHandler(File root) {
        this.root = root.getAbsoluteFile();
    }

    public FileListHttpHandler(String root) {
        this(new File(root));
    }

    @Override
    public void service(Request request, Response response) throws Exception {
        log.info(String.format("%s %s", request.getMethod(), request.getRequestURI()));
        File file = getFile(request);

        if (file.isFile() && isAjax(request)) {
            renderFileJson(request, response, file);
        } else if (file.isFile()) {
            renderFile(request, response, file);
        } else {
            renderDirectory(file, response);
        }
    }

    private void renderFileJson(Request request, Response response, File file) throws IOException {
        LogFileReader reader = new LogFileReader(file);

        Integer tail = getInteger(request, "tail");
        Integer size = getInteger(request, "size");
        Long start = getLong(request, "start");
        List<Line> lines = Collections.emptyList();
        if(size == null){
            size = 100;
        }

        if (start != null && "backward".equalsIgnoreCase(request.getParameter("direction"))) {
            lines = reader.readBackward(start, size);
        }else if (tail != null) {
            lines = reader.readBackward(file.length(), tail);
        }else if (start != null) {
            lines = reader.readForward(start, size);
        }

        JsonView view = new JsonView();
        view.render(lines, response);

    }

    private Long getLong(Request request , String parameterName){
        if(request.getParameter(parameterName) != null){
            try {
                return Long.valueOf(request.getParameter(parameterName));
            } catch (NumberFormatException ignore) {
            }
        }
        return null;
    }

    private Integer getInteger(Request request , String parameterName){
        if(request.getParameter(parameterName) != null){
            try {
                return Integer.valueOf(request.getParameter(parameterName));
            } catch (NumberFormatException ignore) {
            }
        }
        return null;
    }

    private void renderFile(Request request, Response response, File file) throws IOException, TemplateException {
        Model model = new Model();
        model.put("directory", getRelativeDirectory(file.getParentFile()));
        model.put("file", file);
        model.put("tail", getInteger(request ,"tail"));

        View view = new View("file.ftl");
        view.render(model, response);
    }

    private String getRelativeDirectory(File file) {
        String absolutePath = file.getAbsolutePath();
        absolutePath = absolutePath.substring( root.getAbsolutePath().length());
        return absolutePath;
    }

    private void renderDirectory(File directory, Response response) throws IOException, TemplateException {

        File[] listFiles = directory.listFiles(FILE_FILTER);
        Arrays.sort(listFiles);

        Model model = new Model();
        model.put("directory", getRelativeDirectory(directory));
        model.put("files", listFiles);

        View view = new View("files.ftl");
        view.render(model, response);
    }

    private File getFile(Request request) {
        String uri = request.getRequestURI().substring(request.getContextPath().length());
        uri = uri.replaceAll("\\.\\./", "");
        File file = new File(root, uri);

        if (!FILE_FILTER.accept(file)) {
            file = root;
        }
        return file;
    }


    private boolean isAjax(Request request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }


}
