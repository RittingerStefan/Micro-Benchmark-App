package testbench;

import timing.ITimer;
import timing.Timer;

import logging.ILogger;
import logging.ConsoleLogger;

import bench.DemoBenchmarkInsertSort;
import bench.IBenchmark;

public class DemoTestbenchInsertSort {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new DemoBenchmarkInsertSort();

        bench.initialize(1000000);
        timer.start();
        bench.run();
        long time = timer.stop();

        log.write("Finished in " + time + " ns");
        log.close();
        bench.clean();
    }
}
