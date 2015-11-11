package com.hoangnhm.sodukusolver;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by vantuegia on 11/7/2015.
 */

public class Solver {

    private static final String TAG = "S_DEBUG";
    private ArrayList<int[][]> solutions = new ArrayList<>();
    int countSolution = 0;

    public ArrayList<int[][]> getSolutions() {
        return solutions;
    }

    public void clearSolutions() {
        solutions.clear();
    }

    private void addSolutions(int[][] solution) {
        int[][] newSolution = new int[9][9];
        //System.arraycopy(solution, 0, newSolution, 0, newSolution.length);
        for (int i = 0; i < 9; i++) {
            System.arraycopy(solution[i], 0, newSolution[i], 0, 9);
        }
        this.solutions.add(newSolution);
        countSolution++;
    }

    private boolean isValidRow(int value, int col, int[][] S) {
        for (int i = 0; i < 9; i++) {
            if (value == S[i][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidColumn(int value, int row, int[][] S) {
        for (int i = 0; i < 9; i++) {
            if (value == S[row][i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidZone(int value, int row, int col, int[][] S) {
        int r = row / 3 * 3;
        int c = col / 3 * 3;
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (value == S[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(int value, int index, int[][] S) {
        int row = index / 9;
        int col = index % 9;
        return isValidRow(value, col, S) && isValidColumn(value, row, S) && isValidZone(value, row, col, S);
    }

    public boolean isValid(int value, int row, int col, int[][] S) {
        return isValidRow(value, col, S) && isValidColumn(value, row, S) && isValidZone(value, row, col, S);
    }

    public int nextEmpty(int[][] S) {
        for (int row = 0; row < 9; row += 1) {
            for (int col = 0; col < 9; col += 1) {
                if (0 == S[row][col]) {
//                    Log.d(TAG, "row: " + row[0] + " ,col: " + col[0]);
                    return (row * 9) + col;
                }
            }
        }
        return 100;
    }

    public boolean solved(int[][] S) {
        int position = nextEmpty(S);
        if (100 == position) {
            return true;
        }
        int row = position / 9;
        int col = position % 9;

        for (int num = 1; num < 10; num++) {
            if (isValid(num, row, col, S)) {
                S[row][col] = num;
                //Log.d(TAG, row[0] + " " + col[0] + ": " + num);
                if (solved(S)) {
                    // TODO save solution, and continue
                    if (countSolution < 2) {
                        addSolutions(S);
                        S[row][col] = 0;
                    } else {
                        return true;
                    }
                } else {
                    S[row][col] = 0;
                }
            }
        }
        return false;
    }
}
