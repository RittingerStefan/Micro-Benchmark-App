package benchmark.cpu;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BaileyBorweinPlouffePiCalculator implements IDigitsOfPiCalculator {
    private static final BigDecimal bigTwo = new BigDecimal(2);
    private static final BigDecimal bigThree = new BigDecimal(3);
    private static final BigDecimal bigFour = new BigDecimal(4);
    private static final BigDecimal bigSixteen = new BigDecimal(16);

    private MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    private int nrOfIterations = 2;
    private boolean isCanceled = false;

    @Override
    public void warmUp() {
        internalCalculatePi(50);
    }

    @Override
    public void configurePiCalculation(int nrOfPiDigits) {
        // CORRECT: nrOfIterations = Math.max(2, (int)Math.ceil(nrOfPiDigits / 1.2));
        nrOfIterations = Math.max(2, (int)Math.ceil(nrOfPiDigits / 100.0));
        mathContext = new MathContext(nrOfPiDigits + 1, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculatePi() {
        return internalCalculatePi(nrOfIterations);
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    private BigDecimal internalCalculatePi(int nrIterations) {
        isCanceled = false;

        // Store the result
        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal sixteenToPowK = BigDecimal.ONE;
        BigDecimal divisor = BigDecimal.ONE;

        // Loop through the Bailey-Borwein-Plouffe formula:
        // pi = sum(from k=0 to infinity)[1/16^k * (4/8*k+1 - 2/8*k+4 - 1/8*k+5 - 1/8*k+6)]
        for (int k = 0; k < nrIterations; k++) {
            if (isCanceled) {
                break;
            }

            BigDecimal term = bigFour.divide(divisor, mathContext); // 4 / 8*k + 1
            divisor = divisor.add(bigThree); // 8k + 4
            term = term.subtract(bigTwo.divide(divisor, mathContext)); // - 2 / 8*k + 4
            divisor = divisor.add(BigDecimal.ONE); // 8k + 5
            term = term.subtract(BigDecimal.ONE.divide(divisor, mathContext)); // - 1 / 8*k + 5
            divisor = divisor.add(BigDecimal.ONE); // 8k + 6
            term = term.subtract(BigDecimal.ONE.divide(divisor, mathContext)); // - 1 / 8*k + 6
            divisor = divisor.add(bigThree); // 8(k+1) + 1

            pi = pi.add(term.multiply(sixteenToPowK), mathContext);
            sixteenToPowK = sixteenToPowK.divide(bigSixteen, mathContext);
        }

        // Return the value of pi
        return pi;
    }
}