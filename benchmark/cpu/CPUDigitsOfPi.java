package benchmark.cpu;

import bench.IBenchmark;

public class CPUDigitsOfPi implements IBenchmark {
    private IDigitsOfPiCalculator calculator;
    private int nrDigits;
    private boolean cancel;

    @Override
    public void run() {
        cancel = false;
        //TODO
    }

    @Override
    public void run(Object... params) {
        initialize(params);
        run();
    }

    @Override
    public void initialize(Object... params) {
        //TODO
        int piAlgorithm = (Integer) params[0];
        nrDigits = (Integer) params[1];

        switch (piAlgorithm) {
            case 0:
                //TODO
                break;
            default:
                throw new IllegalArgumentException("Invalid algorithm: " + piAlgorithm);
        }
    }

    @Override
    public void clean() {
        // Nothing to clean up
    }

    @Override
    public void cancel() {
        cancel = true;
    }

    @Override
    public void warmUp() {
        //TODO
    }
}
