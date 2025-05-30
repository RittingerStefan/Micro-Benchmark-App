package testbench;

import bench.hdd.HDDRandomAccess;
import logging.*;

public class TestHDDRandomAccess {
    public static void main(String[] args) {
        HDDRandomAccess benchmark = new HDDRandomAccess();

        ILogger log = new ConsoleLogger();

        benchmark.initialize(1L * 1024 * 1024 * 1024);

        int[] buffers = {512, 4 * 1024, 64 * 1024, 1024 * 1024};

        for (int buf : buffers) {

            benchmark.run(new Object[]{"r", "fs", buf});
            log.write("READ FS - Buffer " + buf + " bytes: " + benchmark.getResult());

            benchmark.run(new Object[]{"r", "ft", buf});
            log.write("READ FT - Buffer " + buf + " bytes: " + benchmark.getResult());
        }

        benchmark.clean();
    }
}
