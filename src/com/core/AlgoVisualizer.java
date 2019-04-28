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
                //TOBE implemented
                break;
            case "QUICK":
                //TOBE implemented
                break;
            case "MERGE_BOTTOMUP":
                //TOBE implemented
                break;
            case "MERGE_TOPDOWN":
                //TOBE implemented
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
    public static void main(String[] args) {
        // write your code here
        /* Put JUI thread into a new thread
            called: event distribute thread (Dispatcher)
         */
        int N = 100;
        int sceneWidth = 800;
        int sceneHeight = 800;
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "SELECTION");
        //new AlgoVisualizer(sceneWidth, sceneHeight, N, "INSERTION", SortData.Type.NearlyOrdered);
        new AlgoVisualizer(sceneWidth, sceneHeight, N, "INSERTION_IMPROVED", SortData.Type.NearlyOrdered);
    }
}
