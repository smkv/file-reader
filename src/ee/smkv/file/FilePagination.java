package ee.smkv.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FilePagination {
    private final File file;
    private final int pageSize;

    public FilePagination(File file, int pageSize) {
        this.file = file;
        this.pageSize = pageSize;
    }

    public long getFileSize() {
        return file.length();
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageCount() {
        int count = (int) (getFileSize() / getPageSize());
        if (getFileSize() % getPageSize() != 0) count++;
        return count;
    }

    public String getPageAsString(int pageNumber) throws IOException {
        return new String(getPage(pageNumber));
    }

    public byte[] getPage(int pageNumber) throws IOException {
        int fromPosition = (int) Math.min(Math.max(pageNumber * pageSize, 0), getFileSize());
        int count = (int) Math.min(this.pageSize, getFileSize() - fromPosition);
        if (count == 0) {
            return new byte[0];
        }
        return readBytes(fromPosition, count);
    }

    protected byte[] readBytes(long fromPosition, int count) throws IOException {
        byte[] data = new byte[count];
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            FileChannel channel = randomAccessFile.getChannel().position(fromPosition);
            ByteBuffer bb = ByteBuffer.allocate(Math.min(8192, count));
            int read;
            int cursor = 0;
            while ((read = channel.read(bb)) != -1) {
                if (read == 0) continue;
                bb.position(0);
                bb.limit(Math.min(read, count - cursor));
                while (bb.hasRemaining()) {
                    data[cursor++] = bb.get();
                }
                bb.clear();
                if (cursor >= count) break;
            }
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return data;
    }

}
