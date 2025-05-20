import bench.hdd.HDDWriteSpeed;

public class BenchmarkRunner {
    public static void main(String[] args) {
        HDDWriteSpeed benchmark = new HDDWriteSpeed();

        // Run the benchmark with fixed buffer size (file = 512MB)
        benchmark.run("fb", true);

        // Run the benchmark with fixed file size (buffer = 1KB)
        benchmark.run("fs", true);
    }
}
