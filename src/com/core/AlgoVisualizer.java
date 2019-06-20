package com.core;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*初始化视图.*/
/* Set up data. */
/*Controller. Connect data layer and view layer.*/
public class AlgoVisualizer {
    private int DEPLAY = 10;
    private SortData data;
    private String selectedAlgo;
    private AlgoFrame frame;
    private boolean isAnimated = true;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, String selectedAlgo, SortData.Type dataType) {
        /* Initialize data. */
        this.selectedAlgo = selectedAlgo;
        // TODO: Initialize data
        /* Tasks on the event dispatch thread must finish quickly;
         * if they don't, unhandled events back up and the user interface becomes unresponsive.
         */
        data = new SortData(N, sceneHeight, dataType);
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome Algorithm visualization", sceneWidth, sceneHeight, selectedAlgo);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }
    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, String selectedAlgo) {
        this(sceneWidth, sceneHeight, N, selectedAlgo, SortData.Type.Default);
    }
    private void run() {
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
        switch (selectedAlgo) {
            case "SELECTION":
                /* Selection sort minimize the swap operation, ONLY N TIMES.*/
                setDataSelection(0, -1, -1);
                for (int i = 0; i < data.N(); i++) {
                    int minIndex = i;
                    setDataSelection(i, -1, minIndex);
                    for (int j = i + 1; j < data.N(); j++) {
                        setDataSelection(i, j, minIndex);
                        if (data.get(j) < data.get(minIndex)) {
                            minIndex = j;
                            setDataSelection(i, j, minIndex);
                        }
                    }
                    data.swap(minIndex, i);
                    setDataSelection(i + 1, -1,-1);
                    frame.render(data);
                    AlgoVisHelper.pause(DEPLAY);
                }
                setDataSelection(data.N(), -1, -1);
                break;
            case "QUICKSORT_IMPROVE":
                //TOBE implemented
                break;
            case "QUICKSORT":
                //TOBE implemented
                setDataQuickSort(-1, -1, -1, -1, -1);
                /*
                System.out.print("Before quick sort:");
                for (int i = 0; i < data.N(); i++) {
                    System.out.print(" " + data.get(i));
                }
                System.out.println();
                */
                __quickSort(0, data.N() - 1);
                /*
                System.out.print("After quick sort:");
                for (int i = 0; i < data.N(); i++) {
                    System.out.print(" " + data.get(i));
                }
                System.out.println();
                */
                setDataQuickSort(-1, -1, -1, -1, -1);
                break;
            case "MERGE_BOTTOMUP":
                /*Bottom up, don't need access elements using index, good for LinkedList sort.*/
                setDataMergeSort(-1, -1, -1);
                _mergeSortBottomUp();
                setDataMergeSort(0, data.N() - 1, data.N() - 1);
                break;
            case "MERGE_TOPDOWN":
                setDataMergeSort(-1, -1,-1);
                __mergeSort(0, data.N() - 1);
                setDataMergeSort(0, data.N() - 1, data.N() - 1);
                break;
            case "INSERTION_IMPROVED":
                /* For nearly ordered array, insertion sort is o(n).*/
                /* For O(n)^2 algorithm, but 2*n^2 and 12*n^2's difference is huge, insertion sort's constant is smaller.*/
                /* For smaller n, insertion sort has advantage over some nlogn algorithms.*/
                setDataInsertion(0, -1);
                for (int i = 0; i < data.N(); i++) {
                    setDataInsertion(i, i);
                    int currData = data.get(i);
                    int j = i;
                    for (j = i; j > 0 && data.get(j - 1) > currData; j--) {
                        /*No swap.*/
                        data.set(j, data.get(j - 1));
                        setDataInsertion(i + 1, j-1);
                    }
                    data.set(j, currData);
                    setDataInsertion(j, i);
                }
                setDataInsertion(data.N(), -1);
                break;
            case "INSERTION":
                setDataInsertion(0, -1);
                for (int i = 0; i < data.N(); i++) {
                    for (int j = i; j > 0 && data.get(j - 1) > data.get(j); j--) {
                        data.swap(j, j - 1);
                        setDataInsertion(i + 1, j-1);
                    }
                }
                setDataInsertion(data.N(), -1);
                break;
            default:
        }
    }
    private void setDataInsertion(int orderedIndex, int currentIndex){
        data.orderedIndex = orderedIndex;
        data.currCompareIndex = currentIndex;
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
    }
    private void setDataSelection(int orderedIndex, int compareIndex, int currMinIndex) {
        data.currCompareIndex = compareIndex;
        data.orderedIndex = orderedIndex;
        data.currMinIndex = currMinIndex;
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
    }
    private void setDataMergeSort(int l, int r, int mergeIndex) {
        data.l = l;
        data.r = r;
        data.mergeIndex = mergeIndex;
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
    }
    private void setDataQuickSort(int l, int r, int fixedPivot, int curPivot, int curElement) {
        data.l = l;
        data.r = r;
        if (fixedPivot != -1) {
            data.fixedPivots[fixedPivot] = true;
        }
        data.currentPivot = curPivot;
        data.currentElement = curElement;
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
    }
    private class AlgoKeyListener extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == ' ') {
                isAnimated = !isAnimated;
            }
        }
    }
    private class AlgoMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            /*Menu bar height.*/
            e.translatePoint(0,
                    -frame.getBounds().height + frame.getCanvasHeight());
            System.out.println(e.getPoint());
        }
    }
    private void __mergeSort(int l, int r) {
        if (l >= r) return;
        setDataMergeSort(l, r, -1);
        int mid = l + (r - l) / 2;
        __mergeSort(l, mid);
        __mergeSort(mid + 1, r);
        __merge(l, mid, r);
    }
    private void _mergeSortBottomUp() {
        for (int sz = 1; sz <= data.numbers.length; sz *= 2) {
            for (int i = 0; i < data.numbers.length; i += sz + sz) {
                __merge(i, i + sz - 1, Math.min(i + sz + sz - 1, data.numbers.length - 1));
            }
        }
    }
    private void __merge(int l, int mid, int r) {
        int[] aux = new int[r - l + 1];
        for (int i = l; i <= r; i++) {
            aux[i - l] = data.numbers[i];
        }
        int index = l;
        int left = l;
        int right = mid + 1;
        while (index <= r) {
            if (left > mid) {
                data.numbers[index++] = aux[right - l];
                right++;
            } else if (right > r) {
                data.numbers[index++] = aux[left - l];
                left++;
            } else if (aux[left - l] > aux[right - l]) {
                data.numbers[index++] = aux[right - l];
                right++;
            } else {
                data.numbers[index++] = aux[left - l];
                left++;
            }
            setDataMergeSort(l, r, index);
        }
    }
    public void __quickSort(int l, int r) {
        if (l > r) return;
        if (l == r) {
            setDataQuickSort(l, r, l, -1, -1);
        }
        //int p = __partition(l, r);
        //int p = __partition_2_way(l, r);
        int p = __partition_3_way(l, r);
        __quickSort(l, p - 1);
        __quickSort(p + 1, r);
    }

    public int __partition_3_way(int l, int r) {
        data.swap(l, (int) (Math.random() * (r - l + 1)) + l);
        /* Let's say i is the element we currently in process
        arr[l + 1 ... lt] < v
        arr[gt ... r] > v
        arr[lt + 1...i - 1] == v. */
        int pivot = l;
        int lt = l;
        int bt = r + 1;
        int i = l + 1;
        setDataQuickSort(l, r, -1, l, -1);
        while(i < bt) {
            setDataQuickSort(l, r, -1, -1, i);
            if (data.get(i) > data.get(pivot)) {
                data.swap(i, --bt);
            } else if (data.get(i) < data.get(pivot)) {
                data.swap(i++, ++lt);
                setDataQuickSort(l, r, -1, l, i);
            } else {
                i++;
                setDataQuickSort(l, r, -1, l, i);
            }
        }
        data.swap(pivot, lt);
        setDataQuickSort(l, r, lt, -1, i);
        return lt;
    }

    public int __partition_2_way (int l, int r) {
        /*Quick sort partition 2 way. */
        /*Evenly distributed the elements even majority of them are same. */
        setDataQuickSort(l, r, -1, -1, -1);
        data.swap(l, (int) (Math.random() * (r - l +1)) + l);
        setDataQuickSort(l, r, -1, l, -1);
        int p1 = l + 1;
        int p2 = r;
        int pivot = data.get(l);
        while (true) {
            while(p1 <= r && data.get(p1) <= pivot) {
                setDataQuickSort(l, r, -1, l, p1);
                p1++;
            }
            while(p2 >= l + 1 && data.get(p2) >= pivot) {
                setDataQuickSort(l, r, -1, l, p2);
                p2--;
            }
            if (p1 >= p2) break;
            data.swap(p1, p2);
            p1++;
            p2--;
        }
        data.swap(p2, l);
        setDataQuickSort(l, r, p2, -1, -1);
        return p2;
    }
    public int __partition (int l, int r) {
        /*For majority of the element is different from each other.*/
        data.swap(l, (int) (Math.random() * (r - l + 1)) + l);
        int pivot = data.numbers[l];
        setDataQuickSort(l, r, -1, l, -1);
        int j = l;
        for (int i = l + 1; i <= r; i++) {
            setDataQuickSort(l, r, -1, l, i);
            if (data.numbers[i] < pivot) {
                data.swap(j + 1, i);
                j++;
                setDataQuickSort(l, r, -1, l, i);
            }
        }
        data.swap(l, j);
        setDataQuickSort(l, r, j, -1, -1);
        return j;
    }
    public static void main(String[] args) {
        // write your code here
        /* Put JUI thread into a new thread
            called: event distribute thread (Dispatcher)
         */
        int N = 300;
        int sceneWidth = 1200;
        int sceneHeight = 800;
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "SELECTION");
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "INSERTION", SortData.Type.NearlyOrdered);
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "INSERTION_IMPROVED", SortData.Type.NearlyOrdered);
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "MERGE_TOPDOWN", SortData.Type.Default);
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "MERGE_BOTTOMUP", SortData.Type.Default);
        new AlgoVisualizer(sceneWidth, sceneHeight, N, "QUICKSORT", SortData.Type.Default);
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "QUICKSORT", SortData.Type.NearlyOrdered);
    }
}
