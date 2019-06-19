package com.core;
import javax.swing.*;
import java.awt.*;

/* 将实现算法的数据进行绘制.*/
/*View.*/
public class AlgoFrame extends JFrame {
    private int canvasWidth;
    private int canvasHeight;
    private String selectedAlgo;
    public AlgoFrame(String title, int canvasWidth, int canvasHeight, String selectedAlgo) {
        super(title);
        this.canvasHeight = canvasHeight;
        this.canvasWidth = canvasWidth;
        this.selectedAlgo = selectedAlgo;
        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        /*Set current window's cohntent pane.*/
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public int getCanvasWidth() {
        return canvasWidth;
    }
    public int getCanvasHeight() {
        return canvasHeight;
    }
    private SortData data;
    public void render(SortData data) {
        this.data = data;
        this.repaint();
    }
    private class AlgoCanvas extends JPanel {
        public AlgoCanvas() {
            super(true);
            /* Double buffered.*/
            /* How to achieve double buffer from low level point of view? */
        }
        @Override
        public void paintComponent(Graphics g) {
            /* Painting context.*/
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            /* Anti-Aliased.*/
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(hints);
            int w = 0;
            /* Drawing.*/
            switch (selectedAlgo) {
                case "SELECTION":
                    w = canvasWidth / data.N();
                    for (int i = 0; i < data.N(); i++) {
                        if (i < data.orderedIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                        } else {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
                        }
                        if (i == data.currCompareIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                        }
                        if (i == data.currMinIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                        }
                        AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));
                    }
                    break;
                case "QUICKSORT":
                    w = canvasWidth / data.N();
                    for (int i = 0; i < data.N(); i++) {
                        if (i >= data.l && i <= data.r) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Green);
                        } else {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
                        }
                        if (i == data.currentPivot) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                        }
                        if (i == data.currentElement) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                        }
                        if (data.fixedPivots[i]) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                        }
                        AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));
                    }
                    break;
                case "MERGE_BOTTOMUP":
                case "MERGE_TOPDOWN":
                    w = canvasWidth / data.N();
                    for (int i = 0; i < data.N(); i++) {
                        if (i > data.l && i <= data.r) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Green);
                        } else {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
                        }
                        if (i >= data.l && i <= data.mergeIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                        }
                        AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));
                    }
                    break;
                case "INSERTION_IMPROVED":
                case "INSERTION":
                    w = canvasWidth / data.N();
                    for (int i = 0; i < data.N(); i++) {
                        if (i < data.orderedIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                        } else {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);
                        }
                        if (i == data.currCompareIndex) {
                            AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                        }
                        AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));

                    }
                    break;
                default:
            }
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}
