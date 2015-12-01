package ee.smkv.file;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class FilePaginationTest {

    @Test
    public void testTestFile() throws Exception {
        File file = new File("test/ee/smkv/file/test.txt");
        FilePagination pagination = new FilePagination(file, 100);
        assertEquals(2568 , pagination.getFileSize());
        assertEquals(26 , pagination.getPageCount());
        assertEquals("Lorem ipsum dolor sit amet, vestibulum vitae sapien pellentesque suspendisse a neque, amet ligula ve" , pagination.getPageAsString(0));
        assertEquals("l. Odio suscipit etiam eget wisi. Sodales ut nam arcu sodales, fringilla consequat sed fringilla lob" , pagination.getPageAsString(1));
    }



    @Test
    public void testGetPageCount() throws Exception {
        assertEquals(0, new FixedSizeFilePagination(0, 100).getPageCount());
        assertEquals(1, new FixedSizeFilePagination(100, 100).getPageCount());
        assertEquals(2, new FixedSizeFilePagination(120, 100).getPageCount());
    }

    private class FixedSizeFilePagination extends FilePagination {
        private final int size;

        public FixedSizeFilePagination(int size, int pageSize) {
            super(null, pageSize);
            this.size = size;
        }

        @Override
        public long getFileSize() {
            return size;
        }
    }
}