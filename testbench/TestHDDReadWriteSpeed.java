package testbench;

import bench.hdd.HDDWriteSpeed;
import bench.hdd.HDDReadSpeed;
import bench.IBenchmark;

public class TestHDDReadWriteSpeed {
    public static void main(String[] args) {
        // Set to true if you want files deleted after each benchmark
        Boolean clean = Boolean.TRUE;

        System.out.println("=== WRITE TEST: fixed file size, variable buffer ===");
        IBenchmark write = new HDDWriteSpeed();
        write.run("fs", clean); // "fs" = fixed file size

        System.out.println("\n=== WRITE TEST: fixed buffer, variable file size ===");
        write.run("fb", clean); // "fb" = fixed buffer size

        System.out.println("\n=== READ TEST: fixed file size, variable buffer ===");
        IBenchmark read = new HDDReadSpeed();

        read.run("fs", Boolean.FALSE); //i put here manually because if i put true, it deletes the files before completing the full benchmark run

        read.run("fs", Boolean.FALSE);

        System.out.println("\n=== READ TEST: fixed buffer, variable file size ===");
        read.run("fb", clean);
    }

}


