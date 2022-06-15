package util;

public class RandomInt {
    public static int randInt(int min, int max) {
        double randomDouble = Math.random();
        randomDouble = randomDouble * (max - min + 1) + min;
        return (int) randomDouble;
    }

    public static int[] randIntsDifference(int min, int max, int amount) {
        int[] arr = new int[max - min + 1];
        for (int i = 0; i < max - min + 1; i++)
            arr[i] = min + i;
        for (int i = 0; i < max - min + 1; i++) {
            int j = randInt(0, max - min);
            int k = randInt(0, max - min);
            int tmp = arr[j];
            arr[j] = arr[k];
            arr[k] = tmp;
        }
        int[] ans = new int[amount];
        for (int i = 0; i < amount; i++)
            ans[i] = arr[i];
        return ans;
    }
}

