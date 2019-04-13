package com.company;

public class SelectionSortData {
    private int[] numbers;
    public int orderedIndex = -1; //[0 ... ORDERED IDNEX)
    public int currMinIndex = -1; // Current min index
    public int currCompareIndex = -1;
    public SelectionSortData(int N, int randomBound) {
        numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = (int) (Math.random() * randomBound) + 1;
        }
    }
    public int N() { return numbers.length; }
    public int get(int index) {
        if (index < 0 || index >= numbers.length) {
            throw new IllegalArgumentException("Invalid index to access Sort data!");
        }
        return numbers[index];
    }
    public void swap(int i, int j) {
        int tmp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = tmp;
    }
}
