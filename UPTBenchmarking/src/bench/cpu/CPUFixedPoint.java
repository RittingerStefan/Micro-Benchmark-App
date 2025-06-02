package bench.cpu;

import bench.IBenchmark;

public class CPUFixedPoint implements IBenchmark {

    private long result;
    private int size;
    int op;

    int num[] = {0, 1, 2, 3};
    int[] res;


    @Override
    public void initialize(Object... params) {
        this.size = (Integer) params[0];
        this.res = new int[size];
    }


    @Override
    public void warmUp() {
        for (int i = 0; i < size; ++i) {
            result = i; // fixed
            result = (int) (i * 3.1416f); // floating
        }
    }

    @Override
    @Deprecated
    public void run() {
    }

    private int runHelper(Object... options) {
        int count = 0;
        switch ((Operation) options[0]) {
            case Branch:
                int j = 0;
                for (int i = 0; i < size; ++i) {
                    op += 17;
                    if (j == 1) { //1
                        j = num[2]; //2
                    } else {
                        j = num[3]; //2
                    }
                    if (j > 2) { //1
                        j = num[0]; //2
                    } else {
                        j = num[1]; //2
                    }
                    if (j < 1) { //1
                        j = num[1]; //2
                    } else {
                        j = num[0]; //2
                    }
                }
                break;
            case IntegerArithmetic:
                for (int i = 0; i < size; ++i) {
                    try {
                        int a = (i * 17 + 1234) % 1000000;     // 2 ops (*, +) + 3 op (%)
                        int b = (a + i * 23) / 3;              // 2 ops (*, +) + 3 op (/)
                        int c = (a + b) % 1000000;             // 1 op (+) + 3 op (%)

                        int index1 = (a + b + c) % size;       // 2 ops (+, +) + 3 op (%)
                        int index2 = (b * 2 + 5) % size;       // 2 ops (*, +) + 3 op (%)

                        res[index1] = (a * b + c) % 1000000;   // 2 ops (*, +) + 3 op (%)
                        res[index2] = (b + c) * a % 1000000;   // 2 ops (+, *) + 3 op (%)

                        result = res[index1];
                        op += 36; // Total operations counted below
                    } catch (IndexOutOfBoundsException e) {
                        count++;
                        System.out.println("Index out of bounds: " + i);
                    }
                }
                //System.out.println("Out of bounds " + count + " out of " + size);
                break;
            case ArrayAccess:
                int[] a = new int[size];
                int[] b = new int[size];
                int[] c = new int[size];

                // Initialize arrays
                for (int i1 = 0; i1 < size; ++i1) {
                    a[i1] = i1;
                    b[i1] = size - 1 - i1;
                    op += 2;
                }

                // c[i] = a[b[i]]
                for (int i2 = 0; i2 < size; ++i2) {
                    c[i2] = a[b[i2]];
                    op += 2;
                }

                // Swap elements of a and b
                for (int i3 = 0; i3 < size; ++i3) {
                    int tmp = a[i3];
                    a[i3] = b[i3];
                    b[i3] = tmp;
                    op += 3;
                }

                result = c[size - 1];
                break;
            default:
                break;
        }
        return op;
    }


    @Override
    public void run(Object... options) {
        long startTime = System.nanoTime();

        op = runHelper(options);

        long endTime = System.nanoTime();
        long elapsedTimeNs = endTime - startTime;
        double elapsedTimeSec = elapsedTimeNs / 1000000000.0;

        double mops = op / (elapsedTimeSec * 1000000.0); // Million Ops/sec
        result = (int) mops; //start
        System.out.println("MOPS: " + result);
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

    public int getOp() {
        return op;
    }
}

//am nevoie de result si de op



