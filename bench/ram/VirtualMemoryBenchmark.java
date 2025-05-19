package bench.ram;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import timing.Timer;
import bench.IBenchmark;

/**
 * Maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower speeds).
 */
public class VirtualMemoryBenchmark implements IBenchmark {

	private String result = "";
	MemoryMapper core;

	@Override
	public void initialize(Object... params) {
		/* not today */
	}

	@Override
	public void warmUp() {
		/* summer is coming anyway */
	}

	@Override
	public void run() {
		throw new UnsupportedOperationException("Use run(Object[]) instead");
	}

	@Override
	public void run(Object... options) {
		// expected: {fileSize, bufferSize}	
		Object[] params = (Object[]) options;
		long fileSize = Long.parseLong(params[0].toString()); // e.g. 2-16GB
		int bufferSize = Integer.parseInt(params[1].toString()); // e.g. 4+KB

		try {
			core = new MemoryMapper("C:\\Users\\ritti\\OneDrive\\Documents\\codeRelatedStuff\\DC\\000_core", fileSize); // change path as needed
			byte[] buffer = new byte[bufferSize];
			Random rand = new Random();

			Timer timer = new Timer();

			// write to VM
			timer.start();
			for (long i = 0; i < fileSize; i += bufferSize) {
				rand.nextBytes(buffer);
				core.put(i, buffer);
			}
			long elapsedTime = timer.stop();
			double speed = (fileSize / 1024 / 1024L)/(elapsedTime * Math.pow(10, -9));
			BigDecimal bd = BigDecimal.valueOf(speed);
			bd = bd.setScale(2, RoundingMode.HALF_UP);

			result = "\nWrote " + (fileSize / 1024 / 1024L)
					+ " MB to virtual memory at " + bd.doubleValue() +  " MB/s";

			// read from VM
			timer.start();
			for (long i = 0; i < fileSize; i += bufferSize) {
				core.get(i, bufferSize);
			}
			elapsedTime = timer.stop();
			speed = (fileSize / 1024 / 1024L)/(elapsedTime * Math.pow(10, -9));
			bd = BigDecimal.valueOf(speed);
			bd = bd.setScale(2, RoundingMode.HALF_UP);

			// append to previous 'result' string
			result += "\nRead " + (fileSize / 1024 / 1024L)
					+ " MB from virtual memory at " + bd.doubleValue() + " MB/s";
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (core != null)
				core.purge();
		}
	}

	@Override
	public void clean() {
		if (core != null)
			core.purge();
	}

	@Override
	public void cancel() {
		System.out.println("But it refused");
	}

	public String getResult() {
		return result;
	}

}
