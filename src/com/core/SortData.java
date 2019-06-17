package com.core;
import java.util.Arrays;

public class SortData {
    public enum Type {
        Default,
        NearlyOrdered
    }
    public int[] numbers;
    public int orderedIndex = -1; //[0 ... ORDERED IDNEX)
    public int currMinIndex = -1; // Current min index
    public int currCompareIndex = -1;
    public int l, r; //Merge quick sort process section
    public int mergeIndex;
    public SortData(int N, int randomBound, Type dataType) {
        numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = (int) (Math.random() * randomBound) + 1;
        }
        if (dataType == Type.NearlyOrdered) {
            Arrays.sort(numbers);
            int swapTime = (int) (0.02 * N);
            for (int i = 0; i < swapTime; i++) {
                int a = (int) (Math.random() * N);
                int b = (int) (Math.random() * N);
                swap(a, b);
            }
        }
    }
    public SortData(int N, int randomwBound) {
        this(N, randomwBound, Type.Default);
    }
    public int N() { return numbers.length; }
    public int get(int index) {
        if (index < 0 || index >= numbers.length) {
            throw new IllegalArgumentException("Invalid index to access Sort data!");
        }
        return numbers[index];
    }
    public void swap(int i, int j) {
        if (i < 0 || i >= numbers.length || j < 0 || j >= numbers.length) {
            throw new IndexOutOfBoundsException();
        }
        int tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }
    public void set(int i, int val) {
        numbers[i] = val;
    }
}
