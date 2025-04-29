package bench;

import java.util.Random;

public class DemoBenchmarkInsertSort implements IBenchmark{
    
    private int[] arr;
    private boolean cancel; // used in case of cancelling the process

    public void initialize(Object...params) {
        int size = 0;
        Random random = new Random();
        cancel = false;

        // check if parameters were correctly given
        if(!(params.length > 0)) return;

        // obtain size of array
        size = (Integer)(params[0]);
        arr = new int[size];

        // generate random values in array
        for(int i = 0; i < size; i++)
            arr[i] = random.nextInt(1000);
    }

    private int binarySearch(int left, int right, int value) {
        if(left == right) return left;
        if(left < right && !cancel) {
            int mid = left - (left - right)/2;
            if(value < arr[mid]) {
                if(mid == 0 || value > arr[mid - 1]) return mid;
                return binarySearch(left, mid - 1, value);
            }
            else if(value >= arr[mid]) return binarySearch(mid + 1, right, value);
        }
        
        // added to avoid an error
        return -1;
    }

    private void leftShift(int start, int end) {
        for(int i = end; i > start && i >= 0 && !cancel; i--)
            arr[i] = arr[i - 1];
    }

    // insert sort
    public void run() {
        for(int i = 0; i < arr.length && !cancel; i++) {
            int current = arr[i];
            // find the index of the insertion
            int index = binarySearch(0, i, current);
            if(index >= 0) {
                // make room for the insertion
                leftShift(index, i);
                // insert the new element
                arr[index] = current;
            } // if index is negative, the item is in the correct place
        }
    }

    public void run(Object...params) {
        this.run();
    }

    public void clean() {

    }

    public void cancel() {
        this.cancel = true;
    }
}
