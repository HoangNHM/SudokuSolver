package com.hoangnhm.sodukusolver;

import java.util.ArrayList;

/**
 * Created by vantuegia on 11/7/2015.
 */

public class Solver {

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
            for (int j = 0; j < 9; j++) {
                newSolution[i][j] = solution[i][j];
            }
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
        return (isValidRow(value, col, S) && (isValidColumn(value, row, S)) && (isValidZone(value, row, col, S)));
    }

    public boolean isValid(int value, int row, int col, int[][] S) {
        return (isValidRow(value, col, S) && (isValidColumn(value, row, S)) && (isValidZone(value, row, col, S)));
    }

    public boolean nextEmpty(int[] row, int[] col, int[][] S) {
        for (row[0] = 0; row[0] < 9; row[0]++) {
            for (col[0] = 0; col[0] < 9; col[0]++) {
                if (0 == S[row[0]][col[0]]) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean solved(int[][] S) {
        int[] row = {0};
        int[] col = {0};
        if (!nextEmpty(row, col, S)) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isValid(num, row[0], col[0], S)) {
                S[row[0]][col[0]] = num;
                if (solved(S)) {
                    // TODO save solution, and continue
                    if (countSolution < 1000) {
                        addSolutions(S);
                        S[row[0]][col[0]] = 0;
                    } else {
                        return true;
                    }
                } else {
                    S[row[0]][col[0]] = 0;
                }
            }
        }
        return false;
    }
}
