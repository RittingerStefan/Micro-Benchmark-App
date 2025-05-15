package bench.hdd;

import bench.IBenchmark;

public class HDDReadSpeed implements IBenchmark {

    @Override
    public void initialize(Object... params) {
    }

    @Override
    public void warmUp() {
    }

//    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object...) instead");
    }

    @Override
    public void run(Object... options) {
        FileReader reader = new FileReader();
        String option = (String) options[0];
        Boolean clean = (Boolean) options[1];

        String prefix = "D:\\student\\bench\\write";
        String suffix = ".dat";
        int minIndex = 0;
        int maxIndex = 8;
        long fileSize = 512L * 1024 * 1024; // 512MB
        int bufferSize = 4 * 1024; // 4KB

        try {
            if (option.equals("fs"))
                reader.streamReadFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, clean);
            else if (option.equals("fb"))
                reader.streamReadFixedBufferSize(prefix, suffix, minIndex, maxIndex, bufferSize, clean);
            else
                throw new IllegalArgumentException("Invalid option: " + option);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
    }

    @Override
    public String getResult() {
        return null;
    }
}
