package testbench;


import bench.*;
import bench.cpu.CPUFixedPoint;
import bench.cpu.CPUFixedVsFloatingPoint;
import bench.cpu.NumberRepresentation;
import bench.cpu.Operation;
import logging.*;
import logging.TimeUnit;
import timing.*;
import timing.Timer;

public class TestCPUFixedVsFloatingPoint {

	public static void main(String[] args) {
		ITimer timerFloating = new Timer();
		ITimer timerFixed = new Timer();
		ITimer timerFixedBranch = new Timer();
		ITimer timerFixedIntegerArithmetic = new Timer();
		ITimer timerArrayAccess = new Timer();

		ILogger log = /* new FileLogger("bench.log"); */new ConsoleLogger();
		TimeUnit timeUnit = TimeUnit.Milli;

		IBenchmark bench = new CPUFixedVsFloatingPoint();
		bench.initialize(Integer.valueOf(1000000));
		bench.warmUp();

		timerFloating.start();
		bench.run(NumberRepresentation.FIXED);
		long timeFloating = timerFloating.stop();
		log.writeTime("Fixed points finished in", timeFloating, timeUnit);
		log.write("Result  for fixed points is", bench.getResult());

		System.out.println();

		timerFixed.start();
		bench.run(NumberRepresentation.FLOATING);
		long timeFixed = timerFixed.stop();
		log.writeTime("Float points finished in", timeFixed, timeUnit);
		log.write("Result  for fixed points is", bench.getResult());

		System.out.println();
		System.out.println("BRANCH");

		IBenchmark bench2 = new CPUFixedPoint();
		bench2.initialize(Integer.valueOf(1000000));

		timerFixedBranch.start();
		bench2.run(Operation.Branch);
		long timeFixedBranch = timerFixedBranch.stop();
		log.writeTime("Branch points finished in (ms): ", timeFixedBranch, timeUnit);
		//log.write("Result  for fixed points is", bench2.getResult());

		System.out.println();
		System.out.println("INTEGER ARITHMETIC");

		timerFixedIntegerArithmetic.start();
		bench2.run(Operation.IntegerArithmetic);
		long timeFixedIntegerArithmetic = timerFixedIntegerArithmetic.stop();
		log.writeTime("Fixed Integer arithmetic finished in(ms) :", timeFixedIntegerArithmetic, timeUnit);

		System.out.println();
		System.out.println("ARRAY ACCES");
		timerArrayAccess.start();
		bench2.run(Operation.ArrayAccess);
		long timeArrayAccess = timerArrayAccess.stop();
		log.writeTime("Array access finished in(ms) :", timeArrayAccess, timeUnit);

		bench.clean();
		log.close();
	}
}
