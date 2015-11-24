package com.hoangnhm.sodukusolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vantuegia on 11/6/2015.
 */
public class SudokuAdapter extends BaseAdapter {

//    private static final String TAG = "S_DEBUG";
    public ArrayList<View> SView = new ArrayList<View>(81);
    private Context mContext;
    private int[][] sudoku;

    public SudokuAdapter(Context context, int[][] S) {
        this.mContext = context;
        this.sudoku = S;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 81;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SudokuCellView v = (SudokuCellView) convertView;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if (v == null) {
            v = (SudokuCellView) layoutInflater.inflate(R.layout.cell, parent, false);
            if (SView.size() <= position) {
                SView.add(position, v);
//                Log.d("DEBUGG", "add i= " + i);
            }
        }
        int x = position / 9;
        int y = position % 9;
        TextView textView = (TextView) v.findViewById(R.id.cellText);
        String c = (sudoku[x][y] == 0) ? "" : (sudoku[x][y] + "");
        textView.setText(c);
        return v;
    }


    public void setNum(int index, int num, int[][] S) {
        int x = index / 9;
        int y = index % 9;
        S[x][y] = num;
    }

    public void clear(int[][] S) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                S[i][j] = 0;
            }
        }
        this.setSudoku(S);
    }

//    public static class Solver {
//
//        private ArrayList<int[][]> solutions = new ArrayList<>();
//        int countSolution = 0;
//
//        public ArrayList<int[][]> getSolutions() {
//            return solutions;
//        }
//
//        private void addSolutions(int[][] solution) {
//            int[][] newSolution = new int[9][9];
//            System.arraycopy(solution, 0, newSolution, 0, newSolution.length);
//            this.solutions.add(newSolution);
//            countSolution++;
//
//        }
//
//        private boolean isValidRow(int value, int col, int[][] S) {
//            for (int i = 0; i < 9; i++) {
//                if (value == S[i][col]) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        private boolean isValidColumn(int value, int row, int[][] S) {
//            for (int i = 0; i < 9; i++) {
//                if (value == S[row][i]) {
//                    return false;
//                }
//            }
//            return true;
//        }
//
//        private boolean isValidZone(int value, int row, int col, int[][] S) {
//            int r = row / 3 * 3;
//            int c = col / 3 * 3;
//            for (int i = r; i < r + 3; i++) {
//                for (int j = c; j < c + 3; j++) {
//                    if (value == S[i][j]) {
//                        return false;
//                    }
//                }
//            }
//            return true;
//        }
//
//        public boolean isValid(int value, int index, int[][] S) {
//            int row = index / 9;
//            int col = index % 9;
//            return (isValidRow(value, col, S) && (isValidColumn(value, row, S)) && (isValidZone(value, row, col, S)));
//        }
//
//        public boolean isValid(int value, int row, int col, int[][] S) {
//            return (isValidRow(value, col, S) && (isValidColumn(value, row, S)) && (isValidZone(value, row, col, S)));
//        }
//
//        public boolean nextEmpty(int[] row, int[] col, int[][] S) {
//            for (row[0] = 0; row[0] < 9; row[0]++) {
//                for (col[0] = 0; col[0] < 9; col[0]++) {
//                    if (0 == S[row[0]][col[0]]) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//
//        public boolean solved(int[][] S) {
//            int[] row = {0};
//            int[] col = {0};
//            if (!nextEmpty(row, col, S)) {
//                return true;
//            }
//
//            for (int num = 1; num <= 9; num++) {
//                if (isValid(num, row[0], col[0], S)) {
//                    S[row[0]][col[0]] = num;
//                    if (solved(S)) {
//                        // TODO save solution, and continue
//                        addSolutions(S);
//                        if (countSolution < 100) {
//                            S[row[0]][col[0]] = 0;
//                        } else {
//                            return true;
//                        }
//                    } else {
//                        S[row[0]][col[0]] = 0;
//                    }
//                }
//            }
//            return 0 != this.countSolution;
//        }
//    }
}
