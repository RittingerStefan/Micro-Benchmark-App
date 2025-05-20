package bench.hdd;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import timing.Timer;

public class FileWriter {

    private static final int MIN_BUFFER_SIZE = 1024 * 1;         // 1 KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // 32 MB
    private static final long MIN_FILE_SIZE = 1024 * 1024 * 1;   // 1 MB
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 512; // 512 MB
    private Timer timer = new Timer();
    private Random rand = new Random();
    private double benchScore;

    public void streamWriteFixedFileSize(String filePrefix, String fileSuffix,
                                         int minIndex, int maxIndex, long fileSize, boolean clean)
            throws IOException {

        System.out.println("Stream write benchmark with fixed file size");
        int currentBufferSize = MIN_BUFFER_SIZE;
        benchScore = 0;

        for (int fileIndex = minIndex; fileIndex <= maxIndex; fileIndex++) {
            String fileName = filePrefix + fileIndex + fileSuffix;
            writeFile(fileName, currentBufferSize, fileSize, clean);
            currentBufferSize *= 2; // Double buffer size after each iteration
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File write score on partition " + partition + ": " +
                String.format("%.2f", benchScore) + " MB/sec");
    }

    public void streamWriteFixedBufferSize(String filePrefix, String fileSuffix,
                                           int minIndex, int maxIndex, int bufferSize, boolean clean)
            throws IOException {

        System.out.println("Stream write benchmark with fixed buffer size");
        long currentFileSize = MIN_FILE_SIZE;
        benchScore = 0;

        for (int fileIndex = minIndex; fileIndex <= maxIndex; fileIndex++) {
            String fileName = filePrefix + fileIndex + fileSuffix;
            writeFile(fileName, bufferSize, currentFileSize, clean);
            currentFileSize *= 2; // Double file size after each iteration
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File write score on partition " + partition + ": " +
                String.format("%.2f", benchScore) + " MB/sec");
    }

    private void writeFile(String fileName, int bufferSize, long fileSize, boolean clean) throws IOException {
        File folderPath = new File(fileName.substring(0, fileName.lastIndexOf(File.separator)));
        if (!folderPath.exists()) folderPath.mkdirs();

        final File file = new File(fileName);
        final BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), bufferSize);

        byte[] buffer = new byte[bufferSize];
        long toWrite = fileSize / bufferSize;

        timer.start();
        for (long i = 0; i < toWrite; i++) {
            rand.nextBytes(buffer);
            outputStream.write(buffer);
        }
        outputStream.close();

        printStats(fileName, fileSize, bufferSize);

        if (clean) file.deleteOnExit();
    }

    private void printStats(String fileName, long totalBytes, int bufferSize) {
        final long time = timer.stop(); // in ns
        NumberFormat nf = new DecimalFormat("#.00");

        double seconds = time / 1_000_000_000.0;
        double megabytes = totalBytes / (1024.0 * 1024.0);
        double rate = megabytes / seconds;

        System.out.println("Done writing " + totalBytes + " bytes to file: "
                + fileName + " in " + nf.format(seconds) + " sec ("
                + nf.format(rate) + " MB/sec) with a buffer size of "
                + (bufferSize / 1024) + " KB");

        benchScore += rate;
    }
}

