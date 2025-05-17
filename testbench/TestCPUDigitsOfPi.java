package testbench;

import benchmark.cpu.ChudnovskyPiCalculator;
import benchmark.cpu.IDigitsOfPiCalculator;

import java.math.BigDecimal;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        //TODO
        IDigitsOfPiCalculator piCalc = new ChudnovskyPiCalculator();

        testPi(piCalc, 10);
        testPi(piCalc, 100);
        testPi(piCalc, 1000);
        testPi(piCalc, 10000);
    }

    private static void testPi(IDigitsOfPiCalculator piCalc, int nrOfIterations) {
        long start = System.nanoTime();
        BigDecimal pi = piCalc.calculatePi(nrOfIterations);
        long end = System.nanoTime();

        double durationSec = (double)(end - start) / 1_000_000_000.0;

        String piStr = pi.toString();
        int digits = piStr.length() - 2;
        piStr = piStr.substring(0, 16) + "[...]";
        System.out.println("Pi (" + nrOfIterations + " iter): " + piStr + " (" + digits + " digits)");
        System.out.println("Duration: " + durationSec + " seconds");
    }
}
