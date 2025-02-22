import java.util.Arrays;

public class KthSmallestInvestment {
    public static int kthSmallestProduct(int[] returns1, int[] returns2, int k) {

        long left = (long) returns1[0] * returns2[0];
        long right = (long) returns1[returns1.length - 1] * returns2[returns2.length - 1];
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (countPairs(returns1, returns2, mid) < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return (int) left;
    }
    private static int countPairs(int[] returns1, int[] returns2, long target) {
        int count = 0;
        for (int num : returns1) {
            if (num == 0) {
                if (target >= 0) {
                    count += returns2.length; // All pairs with 0 will have product 0 <= target
                }
            } else if (num > 0) {
                // Find the number of elements in returns2 such that returns2[j] <= target / num
                int low = 0, high = returns2.length - 1;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if ((long) num * returns2[mid] <= target) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
                count += low;
            } else {
                int low = 0, high = returns2.length - 1;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if ((long) num * returns2[mid] <= target) {
                        high = mid - 1;
                    } else {
                        low = mid + 1;
                    }
                }
                count += returns2.length - low;
            }
        }
        return count;
    }
    public static void main(String[] args) {
        int[] returns1 = {2, 5};
        int[] returns2 = {3, 4};
        int k = 2;
        System.out.println(kthSmallestProduct(returns1, returns2, k)); // Output: 8

        returns1 = new int[]{-4, -2, 0, 3};
        returns2 = new int[]{2, 4};
        k = 6;
        System.out.println(kthSmallestProduct(returns1, returns2, k)); // Output: 0
    }
}