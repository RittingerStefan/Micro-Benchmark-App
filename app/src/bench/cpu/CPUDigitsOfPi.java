package bench.cpu;

import bench.IBenchmark;

import java.math.BigDecimal;

public class CPUDigitsOfPi implements IBenchmark {
    private IDigitsOfPiCalculator calculator;
    private BigDecimal piResult;

    public BigDecimal getPreviousPiResult() {
        return piResult;
    }

    @Override
    public void run() {
        piResult = calculator.calculatePi();
    }

    @Override
    public void run(Object... params) {
        initialize(params);
        run();
    }

    @Override
    public void initialize(Object... params) {
        // validate parameters
        if (params.length != 2) {
            throw new IllegalArgumentException("Exactly 2 parameters are expected, got " + params.length);
        }
        if (!(params[0] instanceof Number)) {
            throw new IllegalArgumentException("The 1st parameter must be a number");
        }
        if (!(params[1] instanceof Number)) {
            throw new IllegalArgumentException("The 2nd parameter must be a number");
        }

        int piAlgorithm = ((Number) params[0]).intValue();
        int nrOfPiDigits = ((Number) params[1]).intValue();
        if (nrOfPiDigits < 10) {
            throw new IllegalArgumentException("The nr of PI digits parameter must be >= 10");
        }

        switch (piAlgorithm) {
            case 0:
                // Chudnovsky algorithm
                calculator = new ChudnovskyPiCalculator();
                break;
            case 1:
                // Gauss-Legendre algorithm
                calculator = new GaussLegendrePiCalculator();
                break;
            case 2:
                // Bailey-Borwein-Plouffe algorithm
                calculator = new BaileyBorweinPlouffePiCalculator();
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm: " + piAlgorithm);
        }

        calculator.configurePiCalculation(nrOfPiDigits);
    }

    @Override
    public void clean() {
        // Nothing to clean up
    }

    @Override
    public void cancel() {
        if (calculator == null) {
            throw new IllegalArgumentException("No PI calculator was configured!");
        }
        calculator.cancel();
    }

    @Override
    public void warmUp() {
        if (calculator == null) {
            throw new IllegalArgumentException("No PI calculator was configured!");
        }
        calculator.warmUp();
    }

    @Override
    public String getResult() {
        return String.valueOf(piResult);
    }
}
