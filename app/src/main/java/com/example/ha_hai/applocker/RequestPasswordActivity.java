package com.example.ha_hai.applocker;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by ha_hai on 6/30/2018.
 */

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RequestPasswordActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    EditText edt1, edt2, edt3;
    EditText edt4, edt5, edt6;
    Button btn1, btn2, btn3;
    Button btn4, btn5, btn6;
    Button btn7, btn8, btn9;
    Button btn0, btnXoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_password);

        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        edt3 = findViewById(R.id.edt3);
        edt4 = findViewById(R.id.edt4);
        edt5 = findViewById(R.id.edt5);
        edt6 = findViewById(R.id.edt6);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);
        btnXoa = findViewById(R.id.btnXoa);

        edt1.setOnTouchListener(this);
        edt2.setOnTouchListener(this);
        edt3.setOnTouchListener(this);
        edt4.setOnTouchListener(this);
        edt5.setOnTouchListener(this);
        edt6.setOnTouchListener(this);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnXoa.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                setPassword(0);
                break;
            case R.id.btn1:
                setPassword(1);
                break;
            case R.id.btn2:
                setPassword(2);
                break;
            case R.id.btn3:
                setPassword(3);
                break;
            case R.id.btn4:
                setPassword(4);
                break;
            case R.id.btn5:
                setPassword(5);
                break;
            case R.id.btn6:
                setPassword(6);
                break;
            case R.id.btn7:
                setPassword(7);
                break;
            case R.id.btn8:
                setPassword(8);
                break;
            case R.id.btn9:
                setPassword(9);
                break;
            case R.id.btnXoa:
                deletePassword();
                break;
        }

    }

    private void setPassword(int number) {
        if (TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.setText(number + "");
            edt2.requestFocus();
        } else if (TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText(number + "");
            edt3.requestFocus();
        } else if (TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText(number + "");
            edt4.requestFocus();
        } else if (TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText(number + "");
            edt5.requestFocus();
        } else if (TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText(number + "");
            edt6.requestFocus();
        } else if (TextUtils.isEmpty(edt6.getText().toString())) {
            edt6.setText(number + "");
        }
    }

    private void deletePassword() {
        if (!TextUtils.isEmpty(edt6.getText().toString())) {
            edt6.setText("");
            edt5.requestFocus();
        } else if (!TextUtils.isEmpty(edt5.getText().toString())) {
            edt5.setText("");
            edt4.requestFocus();
        } else if (!TextUtils.isEmpty(edt4.getText().toString())) {
            edt4.setText("");
            edt3.requestFocus();
        } else if (!TextUtils.isEmpty(edt3.getText().toString())) {
            edt3.setText("");
            edt2.requestFocus();
        } else if (!TextUtils.isEmpty(edt2.getText().toString())) {
            edt2.setText("");
            edt1.requestFocus();
        } else if (!TextUtils.isEmpty(edt1.getText().toString())) {
            edt1.requestFocus();
            edt1.setText("");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.edt1:
                disableKeyBoard(edt1, event);
                return true; // consume touch even
            case R.id.edt2:
                disableKeyBoard(edt2, event);
                return true; // consume touch even
            case R.id.edt3:
                disableKeyBoard(edt3, event);
                return true; // consume touch even
            case R.id.edt4:
                disableKeyBoard(edt4, event);
                return true; // consume touch even
            case R.id.edt5:
                disableKeyBoard(edt5, event);
                return true; // consume touch even
            case R.id.edt6:
                disableKeyBoard(edt6, event);
                return true; // consume touch even
        }
        return false;
    }

    private void disableKeyBoard(EditText edt, MotionEvent event) {
        int inType4 = edt.getInputType(); // backup the input type
        edt.setInputType(InputType.TYPE_NULL); // disable soft input
        edt.onTouchEvent(event); // call native handler
        edt.setInputType(inType4); // restore input type
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
