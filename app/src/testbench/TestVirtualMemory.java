package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestVirtualMemory {
    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new VirtualMemoryBenchmark();

        long fileSize = 4L * 1024 * 1024 * 1024;
        int bufferSize = 4 * 1024;

        log.write("File size: " + fileSize/1024/1024/1024 + " GB\n");
        log.write("Buffer size: " + bufferSize/1024 + " KB\n");
        bench.run(fileSize, bufferSize);
        log.write(bench.getResult());

        fileSize = 16L * 1024 * 1024 * 1024;
        bufferSize = 8 * 1024;
        log.write("File size: " + fileSize/1024/1024/1024 + " GB\n");
        log.write("Buffer size: " + bufferSize/1024 + " KB\n");
        bench.run(fileSize, bufferSize);
        log.write(bench.getResult());

        bench.clean();
        log.close();
    }
}
