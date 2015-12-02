package ee.smkv.file;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class LogFileReaderTest {

    @Test
    public void testReadFull() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);
        List<LogFileReader.Line> lines = reader.read(0, 10);
        assertEquals(4 , lines.size());
        assertEquals("Lorem ipsum dolor sit amet, vestibulum vitae sapien pellentesque suspendisse a neque." , lines.get(0).getContent());
        assertEquals("Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales." , lines.get(1).getContent());
        assertEquals("Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." , lines.get(2).getContent());
        assertEquals("Leo imperdiet pede purus dapibus fermentum aliquam, pede tempus pede proin tempus, class mattis." , lines.get(3).getContent());

        assertEquals(0 , lines.get(0).getStartPoint());
        assertEquals(lines.get(0).getEndPoint() , lines.get(1).getStartPoint());
        assertEquals(lines.get(1).getEndPoint() , lines.get(2).getStartPoint());
        assertEquals(lines.get(2).getEndPoint() , lines.get(3).getStartPoint());
        assertEquals( file.length() , lines.get(3).getEndPoint());
    }

    @Test
    public void testReadPart() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);
        List<LogFileReader.Line> lines = reader.read(86, 2);
        assertEquals(2 , lines.size());
        assertEquals("Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales." , lines.get(0).getContent());
        assertEquals("Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." , lines.get(1).getContent());
    }

    @Test
    public void testReadSeries() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);

        LogFileReader.Line line;

        line = reader.read(0, 1).get(0);
        assertEquals("Lorem ipsum dolor sit amet, vestibulum vitae sapien pellentesque suspendisse a neque." , line.getContent());

        line = reader.read(line.getEndPoint(), 1).get(0);
        assertEquals("Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales." , line.getContent());

        line = reader.read(line.getEndPoint(), 1).get(0);
        assertEquals("Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." , line.getContent());

        line = reader.read(line.getEndPoint(), 1).get(0);
        assertEquals("Leo imperdiet pede purus dapibus fermentum aliquam, pede tempus pede proin tempus, class mattis." , line.getContent());

        List<LogFileReader.Line> lines = reader.read(line.getEndPoint(), 1);
        assertTrue(lines.isEmpty());
    }
}