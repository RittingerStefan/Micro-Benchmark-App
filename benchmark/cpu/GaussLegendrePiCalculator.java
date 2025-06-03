package benchmark.cpu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class GaussLegendrePiCalculator  implements IDigitsOfPiCalculator {
    // Constants for future operations
    private static final BigDecimal bigTwo = new BigDecimal(2);
    private static final BigDecimal bigFour = new BigDecimal(4);
    private static final BigDecimal bigOneQuarter = new BigDecimal("0.25");

    private MathContext mathContext = new MathContext(10, RoundingMode.HALF_UP);
    private int nrOfIterations = 2;
    private boolean isCanceled = false;

    @Override
    public void warmUp() {
        internalCalculatePi(50);
    }

    @Override
    public void configurePiCalculation(int nrOfPiDigits) {
        double dblIterations = 1.44 * Math.log(nrOfPiDigits) / Math.log(2);
        nrOfIterations = Math.max(2, (int)Math.ceil(dblIterations));
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

        // Initial values for the Gauss-Legendre algorithm
        BigDecimal a = BigDecimal.ONE; // a₀ = 1
        BigDecimal b = BigDecimal.ONE.divide(sqrt(bigTwo), mathContext); // // b₀ = 1 / sqrt(2)
        // Construct t like this because this way assures that the value is stored without errors in the wanted form and value
        BigDecimal t = bigOneQuarter; // t₀ = 1/4
        BigDecimal p = BigDecimal.ONE; // p₀ = 1

        for (int i = 0; i < nrIterations; i++) {
            if (isCanceled) {
                break;
            }

            // Store the previous value
            BigDecimal prevA = a;

            // aₙ₊₁ = (aₙ + bₙ) / 2
            a = a.add(b).divide(bigTwo, mathContext);

            // bₙ₊₁ = sqrt(aₙ * bₙ)
            b = sqrt(prevA.multiply(b));

            // tₙ₊₁ = tₙ - pₙ * (aₙ - aₙ₊₁)²
            t = t.subtract(p.multiply(prevA.subtract(a).pow(2, mathContext), mathContext));

            // pₙ₊₁ = 2 * pₙ
            p = p.multiply(bigTwo);
        }

        // π ≈ (aₙ₊₁ + bₙ₊₁)² / (4 * tₙ₊₁)
        BigDecimal pi = a.add(b).pow(2, mathContext).divide(t.multiply(bigFour), mathContext);
        return pi;
    }

    private BigDecimal sqrt(BigDecimal value) {
        // Approximation using double precision
        BigDecimal x = BigDecimal.valueOf(Math.sqrt(value.doubleValue()));
        BigDecimal prev;

        // Iterate until the result is stable
        do {
            prev = x;
            x = value.divide(x, mathContext);  // xₙ = value / xₙ₋₁
            x = x.add(prev);                   // xₙ = xₙ + xₙ₋₁
            x = x.divide(bigTwo, mathContext); // xₙ = (xₙ + xₙ₋₁) / 2
        } while(!x.equals(prev));

        return x;
    }
}
