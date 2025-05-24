package testbench;

import bench.IBenchmark;
import benchmark.cpu.*;
import logging.*;
import timing.*;

public class TestCPUDigitsOfPi {
    private static final String[] algorithmNames = new String[] {
        "Chudnovsky", "Gauss-Legendre", "Bailey-Borwein-Plouffe"
    };

    private static final int[] nrOfPiDigits = new int[] {
        50, 100, 1000, 10_000, 100_000
    };

    public static void main(String[] args) {
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new CPUDigitsOfPi();

        for (int algorithm = 0; algorithm < algorithmNames.length; algorithm++) {
            for (int nrDigits : nrOfPiDigits) {
                // Bailey-Borwein-Plouffe is very slow for large numbers of digits!
                if (algorithm == 2 && nrDigits > 10_000) {
                    continue;
                }

                log.write("Computing PI (algorithm: " + algorithmNames[algorithm] +
                        ", nr.digits: " + nrDigits + ") ... ");

                // Initialization and warm-up
                bench.initialize(algorithm, nrDigits);
                bench.warmUp();

                // actual run
                ITimer timer = new Timer();
                timer.start();
                bench.run();
                long timeNanos = timer.stop();
                double millis = timeNanos / 1_000_000.0;
                log.write(algorithmNames[algorithm] + "(nr.digits: " + nrDigits + ") = " + millis + " ms");
            }
        }

        log.close();
        bench.clean();
    }
}
