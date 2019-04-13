package com.company;

public class InsertionSortData {
    private int[] numbers;
    public int orderedIndex = -1; // [0 ... orderedIndex) is ordered.
    public int currentIndex = -1; //
    public InsertionSortData(int N, int randomBound) {
        numbers = new int[N];
        for (int i = 0; i < N; i ++) {
            numbers[i] = (int) (Math.random() * randomBound) + 1;
        }
    }
    public int N() {
        return numbers.length;
    }
    public int get(int index) {
        if (index < 0 || index >= numbers.length) {
            throw new IllegalArgumentException("Invalid Index to access Sort Data.");
        }
        return numbers[index];
    }
    private void swap(int i, int j) {
        if (i < 0 || i >= numbers.length || j < 0 || j >= numbers.length) {
            throw new IllegalArgumentException("Invalid Index to access Sort Data.");
        }
        int tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }
}