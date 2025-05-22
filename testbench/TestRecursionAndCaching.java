package testbench;
import bench.*;
import bench.recursion.RecursivePrimeBenchmark;
import logging.*;

public class TestRecursionAndCaching {
    public static void main(String[] args) {
        int[] unrollLevels = {1, 5, 10, 20};
        int runsPerLevel = 3;
        ILogger log = new ConsoleLogger();

        double totalScore = 0;
        for (int L : unrollLevels) {
            double sumScores = 0;
            for (int i = 0; i < runsPerLevel; i++) {
                RecursivePrimeBenchmark bench = new RecursivePrimeBenchmark(new ConsoleLogger());
                bench.initialize(L);
                bench.warmUp();
                bench.run();
                // Compute score from bench state and runtime (implement timing in your run method)
                double score = bench.getScore(bench.getLastRunTimeMillis());
                sumScores += score;
            }
            double avgScore = sumScores / runsPerLevel;
            log.write("Average score for L = " + L + ": " + avgScore+"\n");
            totalScore += avgScore;
        }
        double finalCompositeScore = totalScore / unrollLevels.length;
        log.write("Final composite benchmark score: " + finalCompositeScore);
        log.close();
    }

}

