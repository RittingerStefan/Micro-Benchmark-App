package bench.hdd;

import java.io.IOException;
import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    @Override
    public void initialize(Object... params) {
        // Not used in this implementation
    }

    @Override
    public void warmUp() {
        // Optional: perform dummy writes if needed
    }

<<<<<<< HEAD
    //    @Override
=======
//    @Override
>>>>>>> dc6cf2d8ce9743c7492a9b765e431e7bbf528337
    public void run() {
        throw new UnsupportedOperationException("Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {
        FileWriter writer = new FileWriter();
        String option = (String) options[0]; // "fs" or "fb"
        Boolean clean = (Boolean) options[1];

        String prefix = "D:\\student\\bench\\write";
        String suffix = ".dat";
        int minIndex = 0;
        int maxIndex = 8;

        try {
            if (option.equals("fs")) {
                // Measure write speed as a function of file size (buffer = 1KB)
                long fileSize = 1L * 1024 * 1024; // starts from 1MB
                writer.streamWriteFixedBufferSize(prefix, suffix, minIndex, maxIndex, 1024, clean);
            } else if (option.equals("fb")) {
                // Measure write speed as a function of buffer size (file = 512MB)
                long fileSize = 512L * 1024 * 1024; // 512MB
                writer.streamWriteFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, clean);
            } else {
                throw new IllegalArgumentException("Argument " + options[0] + " is undefined");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
        // Optional: clean temp files
    }

    @Override
    public String getResult() {
        return null; // results are printed directly
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> dc6cf2d8ce9743c7492a9b765e431e7bbf528337
