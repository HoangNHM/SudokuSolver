package com.hoangnhm.sodukusolver;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private int mIndex;
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

    Button.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (null == mTextView) {
                return;
            }
            setCellNum(view.getId());
            disableBtn(mIndex);
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
        mAdapter.setNum(mIndex, num, mRiddle);
        mAdapter.setSudoku(mRiddle);
    }

    void disableBtn(int index) {
        for (int j = 1; j <= 9; j++) {
            if (!mSolver.isValid(j, index, mRiddle)) { // btn disable list
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

        mRiddle = new int[9][9];
        mSolver = new Solver();
        mAdapter = new SudokuAdapter(this, mRiddle);

        GridView sudokuGrid = (GridView) findViewById(R.id.gridview);
        sudokuGrid.setAdapter(mAdapter);
        sudokuGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mTextView = (TextView)view.findViewById(R.id.cellText);
                mIndex = position;
                for (View v : mAdapter.SView) {
                    v.setBackgroundColor(Color.TRANSPARENT);
                }
                mAdapter.SView.get(position).setBackgroundColor(Color.argb(150,0xa9,0xe2,0xf3));
                disableBtn(position);
            }
        });

    }

    public void Solve(View view) {
        disableBtn(mIndex);
        SolveTask solveTask = new SolveTask();
        Toast.makeText(MainActivity.this, "... finding solutions ...", Toast.LENGTH_SHORT).show();
        solveTask.execute();
    }

    public void Clear(View view) {
        mAdapter.clear();
        disableBtn(mIndex);
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
        protected Boolean doInBackground(Nullable... params) {
            mSolver.countSolution = 0;
            mSolver.clearSolutions();
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
                Toast.makeText(MainActivity.this, "Done. Solutions found: " + mSolver.countSolution, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
