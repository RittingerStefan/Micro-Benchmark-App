package bench.cpu;

import bench.IBenchmark;

public class CPUFixedVsFloatingPoint implements IBenchmark {

	private long result;
	private int size;

	@Override
	public void initialize(Object ... params) {
		this.size = (Integer)params[0];
	}

	@Override
	public void warmUp() {
		for (int i = 0; i < size; ++i) {
			result = i; // fixed
			result = (int)(i * 3.1416f); // floating
		}
	}

	@Override
	@Deprecated	
	public void run() {
	}

	@Override
	public void run(Object ...options) {
		result = 0;

		switch ((NumberRepresentation) options[0]) {
			case FLOATING:
				for (int i = 0; i < size; ++i)
					result += (int)(i * 3.1416f); // simulate floating-point ops
				break;
			case FIXED:
				for (int i = 0; i < size; ++i)
					result += i * 31416L / 10000; // simulate fixed-point ops
				break;
			default:
				break;
		}
	}
	
	@Override
	public void cancel() {
		
	}

	@Override
	public void clean() {
	}

	@Override
	public String getResult() {
		return String.valueOf(result);
	}

	public int getOp(){
		return 0;
	}

}
