package testbench;
import bench.*;
import bench.recursion.RecursivePrimeBenchmark;
import logging.*;

public class Main {
    public static void main(String[] args) {
        int[] unrollLevels = {1, 5, 10, 20};
        int runsPerLevel = 3;

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
            System.out.println("Average score for L = " + L + ": " + avgScore+"\n");
            totalScore += avgScore;
        }
        double finalCompositeScore = totalScore / unrollLevels.length;
        System.out.println("Final composite benchmark score: " + finalCompositeScore);
    }

}

