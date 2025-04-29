package testbench;

import bench.DemoBenchmarkSleep;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;

public class DemoTestbenchSleep {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new DemoBenchmarkSleep();
        int n = 20;

        bench.initialize(n);
        timer.start();
        bench.run();
        long time = timer.stop();

        double offset = (time - n * 1000000) / 1000000.0;
        log.write("The offset between measured and expected time is " + offset + " milliseconds");
        log.close();
        bench.clean();
    }
}
