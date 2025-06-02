package bench;

public interface IBenchmark {
    /**
     * Begins running the process used for benchmarking.
     */
    void run();

    /**
     * Begins running the process used for benchmarking.
     * @param params - represents different things depending on implementation.
     */
    void run(Object...params);

    /**
     * Initializes the benchmarking with whatever information it requires.
     * @param params - represents different things depending on implementation. Will always
     * be information needed to initialize the benchmarking process.
     */
    void initialize(Object...params);

    /**
     * Used after the <b>run</b> method, in case there is a need to clean up after the execution.
     */
    void clean();

    /**
     * Cancels the currently running benchmark process. <br>
     * If no process is running, no action is performed.
     */
    void cancel();

    /**
     * Called right before running the algorithm itself to "warm-up" the task at hand. <br>
     * The warm up should do the exact task as the run method, however it should not be timed. <br>
     * The amount of warm-up data/time should be between 10-100% of the total time. <br>
     * This call should <b>not</b> be benchmarked.
     */
    void warmUp();

    /**
     *
     * @return The result of running the benchmark
     */
    String getResult();
}