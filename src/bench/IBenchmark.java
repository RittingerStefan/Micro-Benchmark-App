package bench;

public interface IBenchmark {
    void initialize(Object... params);
    void warmUp();
    void run(Object... options);
    void clean();
    String getResult();
}
