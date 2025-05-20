package bench.hdd;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import timing.Timer;

public class FileReader {

    private static final int MIN_BUFFER_SIZE = 1024 * 1; // 1 KB
    private static final int MAX_BUFFER_SIZE = 1024 * 1024 * 32; // 32 MB
    private static final long MIN_FILE_SIZE = 1024 * 1024 * 1; // 1 MB
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 512; // 512 MB
    private Timer timer = new Timer();
    private double benchScore;

    public void streamReadFixedFileSize(String filePrefix, String fileSuffix,
                                        int minIndex, int maxIndex, long fileSize, boolean clean) throws IOException {

        System.out.println("Stream read benchmark with fixed file size");
        int currentBufferSize = MIN_BUFFER_SIZE;
        String fileName;
        int fileIndex = 0;
        benchScore = 0;

        while (currentBufferSize <= MAX_BUFFER_SIZE && fileIndex <= maxIndex - minIndex) {
            fileName = filePrefix + fileIndex + fileSuffix;
            readFile(fileName, currentBufferSize);
            currentBufferSize *= 2;
            fileIndex++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File read score on partition " + partition + ": "
                + String.format("%.2f", benchScore) + " MB/sec");

        if (clean) {
            for (int i = minIndex; i <= maxIndex; i++) {
                File f = new File(filePrefix + i + fileSuffix);
                f.delete();
            }
        }
    }

    public void streamReadFixedBufferSize(String filePrefix, String fileSuffix,
                                          int minIndex, int maxIndex, int bufferSize, boolean clean) throws IOException {

        System.out.println("Stream read benchmark with fixed buffer size");
        long currentFileSize = MIN_FILE_SIZE;
        int fileIndex = 0;
        benchScore = 0;

        while (currentFileSize <= MAX_FILE_SIZE && fileIndex <= maxIndex - minIndex) {
            String fileName = filePrefix + fileIndex + fileSuffix;
            readFile(fileName, bufferSize);
            currentFileSize *= 2;
            fileIndex++;
        }

        benchScore /= (maxIndex - minIndex + 1);
        String partition = filePrefix.substring(0, filePrefix.indexOf(":\\"));
        System.out.println("File read score on partition " + partition + ": "
                + String.format("%.2f", benchScore) + " MB/sec");

        if (clean) {
            File dir = new File("D:/student/bench");
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.err.println("Failed to delete: " + file.getAbsolutePath());
                    }
                }
            }
        }
            for (int i = minIndex; i <= maxIndex; i++) {
                File f = new File(filePrefix + i + fileSuffix);
                f.delete();
            }
        }
    private void readFile(String fileName, int bufferSize) throws IOException {
        File file = new File(fileName);
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file), bufferSize);

        byte[] buffer = new byte[bufferSize];
        long bytesRead = 0;
        int read;

        timer.start();
        while ((read = inputStream.read(buffer)) != -1) {
            bytesRead += read;
        }
        long elapsed = timer.stop();
        inputStream.close();

        printStats(fileName, bytesRead, bufferSize, elapsed);
    }

    private void printStats(String fileName, long totalBytes, int bufferSize, long time) {
        NumberFormat nf = new DecimalFormat("#.00");
        double seconds = time / 1_000_000_000.0;
        double megabytes = totalBytes / (1024.0 * 1024.0);
        double rate = megabytes / seconds;

        System.out.println("Done reading " + totalBytes + " bytes from file: "
                + fileName + " in " + nf.format(seconds) + " s ("
                + nf.format(rate) + " MB/sec) with a buffer size of "
                + (bufferSize / 1024) + " KB");

        benchScore += rate;
    }
}

