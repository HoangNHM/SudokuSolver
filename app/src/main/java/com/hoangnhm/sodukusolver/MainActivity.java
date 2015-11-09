package com.hoangnhm.sodukusolver;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mTvMsg;
    private int mCurrentCell;
    private ArrayList<Boolean> mTappedCell;
    private int[][] mRiddle;
    private SudokuAdapter mAdapter;
    private Solver mSolver;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private Button btnPrevious;
    private Button btnNext;
    private int mCurrentSolution = 0;

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (null == mTextView) {
                return;
            }
            setCellNum(view.getId());
            setBtnState();

            if (R.id.btn0 != view.getId()) {
                // not delete, save to tapped list
                mTappedCell.set(mCurrentCell, true);
                mAdapter.SView.get(mCurrentCell).setBackgroundColor(Color.argb(150, 0x00, 0x33, 0x33));
            } else {
                // delete, remove from tapped list
                mTappedCell.set(mCurrentCell, false);
                mAdapter.SView.get(mCurrentCell).setBackgroundColor(Color.argb(150, 0xa9, 0xe2, 0xf3));
            }
        }
    };
    private View.OnClickListener viewMoreListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnPrevious:
                    if (mCurrentSolution > 0) {
                        mCurrentSolution -= 1;
                        btnNext.setEnabled(true);
                    }
                    if (0 == mCurrentSolution) {
                        btnPrevious.setEnabled(false);
                    }
                    break;
                case R.id.btnNext:
                    if (mCurrentSolution < mSolver.countSolution) {
                        mCurrentSolution += 1;
                        btnPrevious.setEnabled(true);
                    }
                    if (mCurrentSolution == mSolver.countSolution) {
                        btnNext.setEnabled(false);
                    }
                    break;
                default:
                    break;
            }
            mRiddle = mSolver.getSolutions().get(mCurrentSolution);
            mAdapter.setSudoku(mRiddle);
            mTvMsg.setText(String.format("Solution: %d", mCurrentSolution + 1));
            setBtnState();
        }
    };

    private void setCellNum(int id) {
        int num = 0;
        switch (id) {
            case R.id.btn0:
                num = 0;
                break;
            case R.id.btn1:
                num = 1;
                break;
            case R.id.btn2:
                num = 2;
                break;
            case R.id.btn3:
                num = 3;
                break;
            case R.id.btn4:
                num = 4;
                break;
            case R.id.btn5:
                num = 5;
                break;
            case R.id.btn6:
                num = 6;
                break;
            case R.id.btn7:
                num = 7;
                break;
            case R.id.btn8:
                num = 8;
                break;
            case R.id.btn9:
                num = 9;
                break;
        }
        mAdapter.setNum(mCurrentCell, num, mRiddle);
        mAdapter.setSudoku(mRiddle);
    }

    void setBtnState() {
        if (null == mTextView) {
            return;
        }
        for (int j = 1; j <= 9; j++) {
            if (!mSolver.isValid(j, mCurrentCell, mRiddle)) { // btn disable list
                switch (j) {
                    case 1:
                        btn1.setEnabled(false);
                        break;
                    case 2:
                        btn2.setEnabled(false);
                        break;
                    case 3:
                        btn3.setEnabled(false);
                        break;
                    case 4:
                        btn4.setEnabled(false);
                        break;
                    case 5:
                        btn5.setEnabled(false);
                        break;
                    case 6:
                        btn6.setEnabled(false);
                        break;
                    case 7:
                        btn7.setEnabled(false);
                        break;
                    case 8:
                        btn8.setEnabled(false);
                        break;
                    case 9:
                        btn9.setEnabled(false);
                        break;
                }
            }else {
                switch (j) {
                    case 1:
                        btn1.setEnabled(true);
                        break;
                    case 2:
                        btn2.setEnabled(true);
                        break;
                    case 3:
                        btn3.setEnabled(true);
                        break;
                    case 4:
                        btn4.setEnabled(true);
                        break;
                    case 5:
                        btn5.setEnabled(true);
                        break;
                    case 6:
                        btn6.setEnabled(true);
                        break;
                    case 7:
                        btn7.setEnabled(true);
                        break;
                    case 8:
                        btn8.setEnabled(true);
                        break;
                    case 9:
                        btn9.setEnabled(true);
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btn0.setOnClickListener(onClickListener);
        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);
        btn9.setOnClickListener(onClickListener);

        btnPrevious = (Button) findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(viewMoreListener);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(viewMoreListener);

        mTvMsg = (TextView) findViewById(R.id.tvMsg);
        mTappedCell = new ArrayList<Boolean>(81);
        for (int i = 0; i < 81; i++) {
            mTappedCell.add(i, false);
        }

        mRiddle = new int[9][9];
        mSolver = new Solver();
        mAdapter = new SudokuAdapter(this, mRiddle);

        GridView sudokuGrid = (GridView) findViewById(R.id.gridview);
        sudokuGrid.setAdapter(mAdapter);
        sudokuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mTextView = (TextView) view.findViewById(R.id.cellText);
                mCurrentCell = position;
                updateGrid(position);
                setBtnState();
            }
        });

        multiMode(false);

    }

    public void Solve(View view) {

        boolean isFull = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (0 == mRiddle[i][j]) {
                    isFull = false;
                    break;
                }
            }
            if (!isFull) {
                break;
            }
        }
        if (isFull) {
            boolean isOk = true;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (!mSolver.isValid(mRiddle[i][j], i, j, mRiddle)) {
                        isOk = false;
                        break;
                    }
                    if (!isOk) {
                        break;
                    }
                }
            }
            if (!isOk) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Please check the input and try again.")
                        .setTitle("\u26A0 Wrong input");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else {
            SolveTask solveTask = new SolveTask();
            solveTask.execute();
        }

    }

    public void Clear(View view) {

        boolean isEmpty = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (0 != mRiddle[i][j]) {
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }

        if (!isEmpty) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    clearTappedCell();
                    mAdapter.clear(mRiddle);
                    setBtnState();

                    mSolver.countSolution = 0;
                    multiMode(false); // disable multi mode view
                    mSolver.clearSolutions();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Set other dialog properties
            builder.setTitle("Clear all?");

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private void multiMode(boolean isMulti) {
        mCurrentSolution = 0;
        btnPrevious.setEnabled(false);
        mTvMsg.setVisibility(!isMulti ? View.INVISIBLE : View.VISIBLE);
        btnPrevious.setVisibility(!isMulti ? View.INVISIBLE : View.VISIBLE);
        btnNext.setVisibility(!isMulti ? View.INVISIBLE : View.VISIBLE);
        if (isMulti) {
            mTvMsg.setText(String.format("Solution: %d", mCurrentSolution + 1));
        }
    }

    private void clearTappedCell() {
        for (int i = 0; i < 81; i++) {
            mTappedCell.set(i, false);
            mAdapter.SView.get(i).setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void updateGrid(int position) {
        for (int index = 0; index < mTappedCell.size(); index++) {
            if (mTappedCell.get(index)) {
                mAdapter.SView.get(index).setBackgroundColor(Color.GRAY);
            } else {
                mAdapter.SView.get(index).setBackgroundColor(Color.TRANSPARENT);
            }
        }
        if (mTappedCell.get(position)) {
            mAdapter.SView.get(position).setBackgroundColor(Color.argb(150, 0x00, 0x33, 0x33));
        } else {
            mAdapter.SView.get(position).setBackgroundColor(Color.argb(150, 0xa9, 0xe2, 0xf3));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private class SolveTask extends AsyncTask<Nullable, Nullable, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSolver.countSolution = 0;
            multiMode(false); // disable multi mode view
            mSolver.clearSolutions();
        }

        @Override
        protected Boolean doInBackground(Nullable... params) {
            mSolver.solved(mRiddle);
            return 0 != mSolver.countSolution;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                mRiddle = mSolver.getSolutions().get(0);
                mAdapter.setSudoku(mRiddle);
                mAdapter.notifyDataSetChanged();
                setBtnState();

//                Toast.makeText(MainActivity.this, "Done. Solutions found: " + mSolver.countSolution, Toast.LENGTH_SHORT).show();

                if (mSolver.countSolution > 1) {
                    multiMode(true); // enable multi mode view
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    String msg = "";
                    if (1000 == mSolver.countSolution) {
                        msg = "This Sudoku has at least:" + mSolver.countSolution + " solutions";
                    } else {
                        msg = "This Sudoku has:" + mSolver.countSolution + " solutions";
                    }
                    builder.setMessage(msg)
                            .setTitle("\u263A Multiple solutions found");

                    // 3. Get the AlertDialog from create()
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            } else if (0 == mSolver.countSolution) {
//                Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("This Sudoku cannot be solve, please check your input and try again.")
                        .setTitle("\u26A0 Wrong input");

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }
    }
}
