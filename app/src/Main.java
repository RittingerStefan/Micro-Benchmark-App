import logging.ConsoleLogger;
import logging.FileLogger;
import logging.ILogger;
import testbench.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void printOptions() {
        System.out.println("Welcome to our Microbenchmark application!");
        System.out.println("Please choose one of the following options:");
        System.out.println("1. Test CPU");
        System.out.println("2. Test Virtual Memory");
        System.out.println("3. Test Disk");
        System.out.println("4. Test Recursion and caching");
        System.out.println("5. Test Everything");
        System.out.println("0. Exit");
    }

    public static int readOption(int min, int max) {
        int option = -1;

        try {
            System.out.println("Please enter the option: ");
            option = Integer.parseInt(br.readLine());
            if(option < min || option > max) option = -1;
        } catch (IOException e) {
            System.out.println("Error reading the option.");
            e.printStackTrace();
            return -1;
        }

        return option;
    }

    public static void main(String[] args) {
        int option = -1;

        printOptions();
        while(option == -1) {
            option = readOption(0, 5);
            if(option == -1) System.out.println("Invalid option!");
        }
        if(option == 0) {
            System.out.println("Exiting...");
            try {
                br.close();
            } catch (IOException e) { e.printStackTrace(); }
            System.exit(0);
        }

        switch (option) {
            case 1:
                TestCPUDigitsOfPi.main(null);
                TestCPUFixedVsFloatingPoint.main(null);
                break;
            case 2:
                TestVirtualMemory.main(null);
                break;
            case 3:
                TestHDDReadWriteSpeed.main(null);
                TestHDDRandomAccess.main(null);
                break;
            case 4:
                TestRecursionAndCaching.main(null);
                break;
            case 5:
                TestCPUDigitsOfPi.main(null);
                TestCPUFixedVsFloatingPoint.main(null);
                TestVirtualMemory.main(null);
                TestHDDReadWriteSpeed.main(null);
                TestHDDRandomAccess.main(null);
                TestRecursionAndCaching.main(null);
                break;
        }

        try {
            br.close();
        } catch (IOException e) { e.printStackTrace(); }
    }
}