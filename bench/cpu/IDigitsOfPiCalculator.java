package bench.cpu;
import java.math.BigDecimal;

/**
 * Interface for algorithms used to calculate PI with a given precision.
 */
public interface IDigitsOfPiCalculator {
    void warmUp();

    /**
     * Configure this algorithm to calculate PI using a given precision.
     *
     * @param nrOfPiDigits Number of digits (after the decimal point) to be used as precision.
     */
    void configurePiCalculation(int nrOfPiDigits);

    /**
     * Calculate PI using the precision configured using configurePiCalculation().
     *
     * @return The value of PI, calculated using the given precision.
     */
    BigDecimal calculatePi();

    void cancel();
}
