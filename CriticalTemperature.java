public class CriticalTemperature {
    // Function to calculate the minimum number of measurements
    public static int minMeasurements(int k, int n) {
        int low = 1, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            int total = 0;
            int comb = 1;
            boolean breakEarly = false;
            for (int i = 1; i <= k; i++) {
                comb = comb * (mid - i + 1) / i; // Efficiently compute C(mid, i)
                total += comb;
                if (total >= n) {
                    breakEarly = true;
                    break;
                
            }}
            if (breakEarly) {
                high = mid; // Try to find a smaller m
            } else {
                low = mid + 1; // Increase m
            }
        }
        return low;
    }
    public static void main(String[] args) {
        System.out.println(minMeasurements(1, 2)); // Output: 2
        System.out.println(minMeasurements(2, 6)); // Output: 3
        System.out.println(minMeasurements(3, 14)); // Output: 4
    }
}