package com.company;
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
    private SelectionSortData data;
    private String selectedAlgo;
    private AlgoFrame frame;
    private boolean isAnimated = true;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N, String selectedAlgo) {
        /* Initialize data. */
        data = new SelectionSortData(N, sceneHeight);
        this.selectedAlgo = selectedAlgo;
        // TODO: Initialize data
        /* Tasks on the event dispatch thread must finish quickly;
         * if they don't, unhandled events back up and the user interface becomes unresponsive.
         */
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Welcome Algorithm visualization", sceneWidth, sceneHeight, selectedAlgo);
            frame.addKeyListener(new AlgoKeyListener());
            frame.addMouseListener(new AlgoMouseListener());
            new Thread(() -> {
                run();
            }).start();
        });
    }
    private void run() {
        frame.render(data);
        AlgoVisHelper.pause(DEPLAY);
        switch (selectedAlgo) {
            case "SELECTION":
                /* Selection sort minimize the swap operation, ONLY N TIMES.*/
                setData(0, -1, -1);
                for (int i = 0; i < data.N(); i++) {
                    int minIndex = i;
                    setData(i, -1, minIndex);
                    for (int j = i + 1; j < data.N(); j++) {
                        setData(i, j, minIndex);
                        if (data.get(j) < data.get(minIndex)) {
                            minIndex = j;
                            setData(i, j, minIndex);
                        }
                    }
                    data.swap(minIndex, i);
                    setData(i + 1, -1,-1);
                    frame.render(data);
                    AlgoVisHelper.pause(DEPLAY);
                }
                setData(data.N(), -1, -1);
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
            case "INSERTION":
                break;
            default:
        }
    }
    private void setData(int orderedIndex, int compareIndex, int currMinIndex) {
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
        AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, "SELECTION");
        //AlgoVisualizer visualizer = new AlgoVisualizer(sceneWidth, sceneHeight, N, "INSERTION");
    }
}
