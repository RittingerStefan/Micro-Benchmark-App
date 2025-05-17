package benchmark.cpu;

import java.math.BigDecimal;
import java.math.MathContext;

public class ChudnovskyPiCalculator implements IDigitsOfPiCalculator {
    private static final BigDecimal bd10005 = new BigDecimal(10_005);
    private static final BigDecimal bd426880 = new BigDecimal(426_880);
    private static final BigDecimal bd13591409 = new BigDecimal(13_591_409);
    private static final BigDecimal bd10939058860032000 = new BigDecimal(10_939_058_860_032_000L);
    private static final BigDecimal bd545140134 = new BigDecimal(545_140_134);
    private static final BigDecimal sqrt10005 = bd10005.sqrt(new MathContext(10_000));

    @Override
    public BigDecimal calculatePi(int nrOfIterations) {
        MathContext mathContext = new MathContext(nrOfIterations * 1000);

        // _, Q1n, R1n = binary_split(1, n)
        BinarySplitResult piBinarySplit = binarySplit(1, nrOfIterations);

        // return (426880 * Decimal(10005).sqrt() * Q1n) / (13591409*Q1n + R1n)
        BigDecimal dividend = bd426880.multiply(sqrt10005).multiply(piBinarySplit.getQ());
        BigDecimal divisor = bd13591409.multiply(piBinarySplit.getQ()).add(piBinarySplit.getR());
        return dividend.divide(divisor, mathContext);
    }

    private BinarySplitResult binarySplit(long a, long b) {
        BigDecimal pab, qab, rab;
        if(b == (a+1)) {
            long n1 = - (6 * a - 1);
            long n2 = 2 * a - 1;
            long n3 = 6 * a - 5;
            // Pab = -(6*a - 1)*(2*a - 1)*(6*a - 5)
            pab = new BigDecimal(n1).multiply(new BigDecimal(n2)).multiply(new BigDecimal(n3));
            // Qab = 10939058860032000 * a**3
            BigDecimal bda = new BigDecimal(a);
            qab = bda.multiply(bda).multiply(bda).multiply(bd10939058860032000);
            // Rab = Pab * (545140134*a + 13591409)
            BigDecimal x = bda.multiply(bd545140134).add(bd13591409);
            rab = pab.multiply(x);
        } else {
            long m = (a + b) / 2;
            // Pam, Qam, Ram = binary_split(a, m)
            BinarySplitResult amBinarySplit = binarySplit(a, m);
            // Pmb, Qmb, Rmb = binary_split(m, b)
            BinarySplitResult mbBinarySplit = binarySplit(m, b);

            // Pab = Pam * Pmb
            pab = amBinarySplit.getP().multiply(mbBinarySplit.getP());
            // Qab = Qam * Qmb
            qab = amBinarySplit.getQ().multiply(mbBinarySplit.getQ());
            // Rab = Qmb * Ram + Pam * Rmb
            BigDecimal rab1 = mbBinarySplit.getQ().multiply(amBinarySplit.getR()); // Qmb * Ram
            BigDecimal rab2 = amBinarySplit.getP().multiply(mbBinarySplit.getR()); // Pam * Rmb
            rab = rab1.add(rab2);
        }

        return new BinarySplitResult(pab, qab, rab);
    }

    private static class BinarySplitResult
    {
        private final BigDecimal p, q, r;

        public BinarySplitResult(BigDecimal pab, BigDecimal qab, BigDecimal rab) {
            this.p = pab;
            this.q = qab;
            this.r = rab;
        }

        public BigDecimal getP() {
            return p;
        }

        public BigDecimal getQ() {
            return q;
        }

        public BigDecimal getR() {
            return r;
        }
    }
}
