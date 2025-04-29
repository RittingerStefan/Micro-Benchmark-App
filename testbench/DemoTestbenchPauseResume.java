package testbench;

import bench.DemoBenchmarkSleep;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class DemoTestbenchPauseResume {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new DemoBenchmarkSleep();
        final int workload = 100;

        bench.initialize(workload);
        timer.start();
        for(int i = 0; i < 12; i++) {
            timer.resume();
            bench.run();
            long time = timer.pause();
            log.writeTime("Run" + i + " : ", time, TimeUnit.MILLI);
        }
        log.writeTime("Finished in ", timer.stop(), TimeUnit.MILLI);

        log.close();
        bench.clean();
    }
}
