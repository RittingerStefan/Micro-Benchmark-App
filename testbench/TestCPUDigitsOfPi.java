package testbench;

import benchmark.cpu.*;

import java.math.BigDecimal;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        // TODO

        System.out.println("\nTesting ChudnovskyPiCalculator...");
        testPiAlgo(new ChudnovskyPiCalculator());

        System.out.println("\nTesting GaussLegendrePiCalculator...");
        testPiAlgo(new GaussLegendrePiCalculator());

        System.out.println("\nTesting BaileyBorweinPlouffePiCalculator...");
        testPiAlgo(new BaileyBorweinPlouffePiCalculator());
    }

    private static void testPiAlgo(IDigitsOfPiCalculator piCalc) {
        testPi(piCalc, 20);
        testPi(piCalc, 100);
        testPi(piCalc, 1_000);
        testPi(piCalc, 10_000);
        testPi(piCalc, 100_000);
    }

    private static void testPi(IDigitsOfPiCalculator piCalc, int nrOfPiDigits) {
        piCalc.configurePiCalculation(nrOfPiDigits);

        long start = System.nanoTime();
        BigDecimal pi = piCalc.calculatePi();
        long end = System.nanoTime();
        double durationSec = (double)(end - start) / 1_000_000_000.0;

        String piStr = pi.toString();
        int digits = piStr.length() - 2;
        // piStr = piStr.substring(0, 16) + "[...]";
        piStr = piStr.length() > 16 ? piStr.substring(0, 16) + "[...]" : piStr;
        System.out.println("Pi (" + nrOfPiDigits + " digits): " + piStr + " (" + digits + " digits)");
        System.out.println("Duration: " + durationSec + " seconds");
    }
}
