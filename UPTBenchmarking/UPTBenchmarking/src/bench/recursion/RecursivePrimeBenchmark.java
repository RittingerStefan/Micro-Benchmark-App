package bench.recursion;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import bench.*;
import logging.*;


public class RecursivePrimeBenchmark implements IBenchmark {

    private int L = 1; // default loop unroll level
    private int lastPrime = 2;
    private int primeCount = 0;
    private boolean cancelled = false;
    private ILog logger;
    private long lastRunTimeMillis = 0;


    public RecursivePrimeBenchmark() {
    }

    public RecursivePrimeBenchmark(ILog logger) {
        this.logger = logger;
    }

    private boolean isPrime(int x) {
        if (x < 2) return false;
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    private void findLPrimes(int x, int L) {
        if (cancelled) return;

        int count = 0;
        while (count < L) {
            if (isPrime(x)) {
                lastPrime = x;
                primeCount++;
                count++;
            }
            x++;
        }
        findLPrimes(x, L); // recurse
    }

    @Override
    public void initialize(Object... params) {
        if (params.length > 0 && params[0] instanceof Integer)
            this.L = (int) params[0];
    }

    @Override
    public void warmUp() {
        try {
            findLPrimes(2, Math.max(1, L / 2)); // smaller warm-up load
        } catch (StackOverflowError ignored) {}
        primeCount = 0;
        lastPrime = 2;
    }

    @Override
    public void run() {
        run(L);
    }

    @Override
    public void run(Object... options) {
        long start = System.nanoTime();
        try {
            findLPrimes(2, L);
        } catch (StackOverflowError e) {
            long end = System.nanoTime();
            long duration = end - start;
            lastRunTimeMillis = duration / 1_000_000; // convert to milliseconds

            if (logger != null) {
                logger.write("Last Prime:", lastPrime, "Prime Count:", primeCount);
                logger.writeTime("Time:", lastRunTimeMillis, logging.TimeUnit.Milli);
            }
        }
    }


    public long getLastRunTimeMillis() {
        return lastRunTimeMillis;
    }

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public void clean() {
        primeCount = 0;
        lastPrime = 2;
        cancelled = false;
    }

    @Override
    public String getResult() {
        return lastPrime + ":" + primeCount;
    }

    // Optional scoring method
    public double getScore(long timeMillis) {
        return (lastPrime * primeCount) / (double) timeMillis;
    }
}

