package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestVirtualMemory {
    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        VirtualMemoryBenchmark bench = new VirtualMemoryBenchmark();

        long fileSize = 4L * 1024 * 1024 * 1024;
        int bufferSize = 4 * 1024;

        bench.run(fileSize, bufferSize);
        log.write(bench.getResult());

        bench.clean();
        log.close();
    }
}
