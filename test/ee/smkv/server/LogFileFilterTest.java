package ee.smkv.server;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class LogFileFilterTest {

    @Test
    public void testAcceptFile() throws Exception {
        LogFileFilter filter = new LogFileFilter();
        assertTrue( filter.accept(new File("application.log")));
        assertTrue( filter.accept(new File("application.LOG")));
        assertFalse( filter.accept(new File("application.any")));
    }

    @Test
    public void testAcceptDir() throws Exception {
        LogFileFilter filter = new LogFileFilter();
        assertTrue( filter.accept(new File("/")));
        assertFalse( filter.accept(new File("./")));
        assertFalse( filter.accept(new File("../")));
    }
}