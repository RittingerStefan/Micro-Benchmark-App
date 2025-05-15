package bench;

public class DemoBenchmarkSleep implements IBenchmark {
    private int n;

    public void initialize(Object...params) {
        if(params.length > 0) n = (int)params[0];
    }

    public void run() {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run(Object...params) {
        this.run();
    }

    public void clean() {

    }

    public void cancel() {
    }

    public void warmUp() {
        this.run();
    }
}
