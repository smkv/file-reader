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
        List<Line> lines = reader.readForward(0, 10);
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
        List<Line> lines = reader.readForward(86, 2);
        assertEquals(2 , lines.size());
        assertEquals("Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales." , lines.get(0).getContent());
        assertEquals("Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." , lines.get(1).getContent());
    }

    @Test
    public void testReadSeries() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);

        Line line;

        line = reader.readForward(0, 1).get(0);
        assertEquals("Lorem ipsum dolor sit amet, vestibulum vitae sapien pellentesque suspendisse a neque." , line.getContent());

        line = reader.readForward(line.getEndPoint(), 1).get(0);
        assertEquals("Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales." , line.getContent());

        line = reader.readForward(line.getEndPoint(), 1).get(0);
        assertEquals("Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." , line.getContent());

        line = reader.readForward(line.getEndPoint(), 1).get(0);
        assertEquals("Leo imperdiet pede purus dapibus fermentum aliquam, pede tempus pede proin tempus, class mattis." , line.getContent());

        List<Line> lines = reader.readForward(line.getEndPoint(), 1);
        assertTrue(lines.isEmpty());
    }

    @Test
    public void testReadUpFull() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);
        List<Line> lines = reader.readBackward(file.length(), 10);
        assertEquals("[" +
                "[0:86] Lorem ipsum dolor sit amet, vestibulum vitae sapien pellentesque suspendisse a neque., " +
                "[86:183] Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales., " +
                "[183:277] Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus., " +
                "[277:373] Leo imperdiet pede purus dapibus fermentum aliquam, pede tempus pede proin tempus, class mattis." +
                "]",lines.toString());

    }
    @Test
    public void testReadUpPart() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);
        List<Line> lines = reader.readBackward(277, 2);
        assertEquals("[" +
                "[86:183] Ullamcorper magna eros maecenas, purus felis faucibus dictumst, enim erat. Sociosqu sed sodales., " +
                "[183:277] Class orci maecenas accumsan eget eu fusce, in accumsan tincidunt mauris mollis, quis tellus." +
                "]",lines.toString());

    }


    @Test
    public void testReadUpZero() throws Exception {
        File file = new File("test/ee/smkv/file/short.txt");
        LogFileReader reader = new LogFileReader(file);
        List<Line> lines = reader.readBackward(0, 2);
        assertEquals("[]",lines.toString());

    }

    @Test
    public void testReadUtf8() throws Exception {

        File file = new File("test/ee/smkv/file/utf8.txt");
        LogFileReader reader = new LogFileReader(file);
        List<Line> lines = reader.readForward(0, 5);
        assertEquals("[[0:21] фывапролдж, [21:46] йцукенгшщзхъ, [46:65] ячсмитьбю, [65:73] iopüõ, [73:79] öää]" , lines.toString());
    }


    @Test
    public void testReadUtf8Up() throws Exception {

        File file = new File("test/ee/smkv/file/utf8.txt");
        LogFileReader reader = new LogFileReader(file);
        List<Line> lines = reader.readBackward(file.length(), 5);
        assertEquals("[[0:21] фывапролдж, [21:46] йцукенгшщзхъ, [46:65] ячсмитьбю, [65:73] iopüõ, [73:79] öää]" , lines.toString());
    }
}