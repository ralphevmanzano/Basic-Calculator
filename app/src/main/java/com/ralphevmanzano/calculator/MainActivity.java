package com.ralphevmanzano.calculator;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private TextView textViewResult;

    private boolean hasOperation = false;
    private boolean hasDot = false;
    private boolean hasNegative = false;
    private boolean canNegative = true;
    private boolean canOperate = false;

    private static final String STATE_HAS_OPERATION = "hasOperation";
    private static final String STATE_HAS_NEGATIVE = "hasNegative";
    private static final String STATE_HAS_DOT = "hasDot";
    private static final String STATE_CAN_NEGATIVE = "canNegative";
    private static final String STATE_CAN_OPERATE = "canOperate";
    private static final String STATE_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
        textViewResult = findViewById(R.id.textViewResult);

        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonEquals = findViewById(R.id.buttonEquals);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        final Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonOpenPar = findViewById(R.id.buttonOpenPar);
        Button buttonClosePar = findViewById(R.id.buttonClosePar);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String idName = b.getResources().getResourceEntryName(b.getId());

                switch (idName) {
                    case "buttonDot":
                        buttonDot(b);
                        break;

                    case "buttonSubtract":
                        buttonSubtract(b);
                    case "buttonAdd":
                    case "buttonMultiply":
                    case "buttonDivide":
                        buttonOperators(b, idName);
                        break;

                    case "buttonDelete":
                        buttonDelete(b);
                        break;

                    case "buttonOpenPar":
                        appendToInput(b);

                        canOperate = false;
                        break;

                    case "buttonClosePar":
                        buttonClosePar(b);
                        break;

                    case "buttonEquals":
                        buttonEquals(b);
                        break;

                    default:
                        buttonNumbers(b);
                }
            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        buttonAdd.setOnClickListener(listener);
        buttonSubtract.setOnClickListener(listener);
        buttonMultiply.setOnClickListener(listener);
        buttonDivide.setOnClickListener(listener);
        buttonDelete.setOnClickListener(listener);
        buttonOpenPar.setOnClickListener(listener);
        buttonClosePar.setOnClickListener(listener);
        buttonEquals.setOnClickListener(listener);

        buttonDelete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editTextInput.setText("");
                textViewResult.setText("");
                hasOperation = false;
                hasNegative = false;
                hasDot = false;
                return false;
            }
        });
    }

    private String getLastChar() throws NullPointerException {
        String input = editTextInput.getText().toString();
        if (input.length() > 0) {
            return input.substring(input.length() - 1);
        } else {
            return null;
        }

    }

    private String getInput() {
        return editTextInput.getText().toString();
    }

    private int getInputLength() {
        return editTextInput.getText().toString().length();
    }

    private void appendToInput(Button b) {
        editTextInput.append(b.getText().toString());
    }

    private void buttonDot(Button b) throws NullPointerException {
        if (!hasDot && !getInput().isEmpty()) {
            if (getLastChar().equals("+") || getLastChar().equals("×") || getLastChar().equals("÷") || getLastChar().equals("-")) {
                editTextInput.append("0" + b.getText().toString());
            } else if (hasNegative) {
                editTextInput.getText().insert(getInputLength() - 1, "0" + b.getText().toString());
            } else {
                appendToInput(b);
            }

            canOperate = false;
            hasDot = true;
        }
        if (getInput().isEmpty()) {
            editTextInput.append("0" + b.getText().toString());

            canOperate = false;
            hasDot = true;
        }
    }

    private void buttonSubtract(Button b) {
        if (getInput().isEmpty()) {
            editTextInput.append("()");
            editTextInput.getText().insert(getInputLength() - 1, "-");

            hasNegative = true;
            canOperate = false;
        } else if (getLastChar().equals("+") || getLastChar().equals("÷") || getLastChar().equals("×")) {
            editTextInput.append("()");
            editTextInput.getText().insert(getInputLength() - 1, "-");

            hasOperation = true;
            hasNegative = true;
            canOperate = false;
        } else if (getLastChar().equals("-") || getLastChar().equals(".") || (getLastChar().equals(")") && !canOperate)) {
            canOperate = false;
        } else {
            appendToInput(b);

            hasNegative = false;
            hasOperation = true;
            canOperate = false;
        }
    }

    private void buttonOperators(Button b, String idName) {
        if (!getInput().isEmpty() && !idName.equals("buttonSubtract")) {
            if (canOperate) {
                appendToInput(b);

                hasOperation = true;
                canOperate = false;
            } else if (!canOperate && !getLastChar().equals(")") && !getLastChar().equals("0")) {
                if (editTextInput.getText().length() > 0) {
                    editTextInput.getText().replace(getInputLength() - 1, getInputLength(), b.getText().toString());
                }
            }
        }

        if (hasDot) {
            hasDot = false;
        }

        if (!idName.equals("buttonSubtract")) {
            hasNegative = false;
        }

        if (!getInput().isEmpty()) {
            if (getLastChar().equals(")") && !idName.equals("buttonSubtract")) {
                if (getInputLength() > 3) {
                    editTextInput.getText().replace(getInputLength() - 4, getInputLength(), b.getText().toString());
                } else {
                    editTextInput.setText("");
                }
            }
        }
    }

    private void buttonDelete(Button b) {
        if (!getInput().isEmpty()) {
            if (getLastChar().equals(".")) {
                hasDot = false;
            }
            if (getLastChar().equals(")") || getLastChar().equals("(")) {
                canOperate = false;
            } else if (getLastChar().equals("+") && getLastChar().equals("-") && getLastChar().equals("×") && getLastChar().equals("÷")) {
                canOperate = true;
            }
            editTextInput.setText(getInput().substring(0, getInputLength() - 1));

            if (!getInput().isEmpty() && hasOperation) {
                if (!getLastChar().equals("+") && !getLastChar().equals("-") && !getLastChar().equals("×") && !getLastChar().equals("÷") && !getLastChar().equals(".")) {
                    performOperation();
                }
            }

            if (getInput().isEmpty()) {
                textViewResult.setText("");
                hasOperation = false;
            }
        }
    }

    private void buttonEquals(Button b) {
        if (textViewResult.getText().toString().equals("Bad Expression")) {
            editTextInput.setText("");
        } else if (!textViewResult.getText().equals("")) {
            editTextInput.setText("");
            editTextInput.append(textViewResult.getText().toString());
        }
        textViewResult.setText("");
    }

    private void buttonClosePar(Button b) {
        appendToInput(b);
        if (hasOperation) {
            performOperation();
        }

        canOperate = true;
    }

    private void buttonNumbers(Button b) {
        if (!hasNegative) {
            appendToInput(b);
        } else if (hasNegative) {
            editTextInput.getText().insert(getInputLength() - 1, b.getText().toString());
        }

        if (hasOperation) {
            performOperation();
        }

        canNegative = false;
        canOperate = true;
    }

    private void performOperation() {
        String expression = getInput().replace('×', '*');
        expression = expression.replace('÷', '/');
        Expression exp = new Expression(expression);

        if (!getInput().equals("2+2-1")) {
            textViewResult.setText(Double.toString(exp.calculate()));

            if (textViewResult.getText().toString().equals("NaN")) {
                textViewResult.setText("Bad Expression");
                textViewResult.setTextColor(Color.parseColor("#A6C9362E"));
                editTextInput.setTextColor(Color.parseColor("#A6C9362E"));
            } else {
                textViewResult.setTextColor(Color.parseColor("#FF5C5C66"));
                textViewResult.setText(Double.toString(exp.calculate()));
                editTextInput.setTextColor(Color.parseColor("#dcd0cdcd"));
            }
        } else {
            textViewResult.setText("that's 3 Quick Maths");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_HAS_NEGATIVE, hasNegative);
        outState.putBoolean(STATE_HAS_DOT, hasDot);
        outState.putBoolean(STATE_HAS_OPERATION, hasOperation);
        outState.putBoolean(STATE_CAN_NEGATIVE, canNegative);
        outState.putBoolean(STATE_CAN_OPERATE, canOperate);
        outState.putString(STATE_RESULT, textViewResult.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        hasNegative = savedInstanceState.getBoolean(STATE_HAS_NEGATIVE);
        hasDot = savedInstanceState.getBoolean(STATE_HAS_DOT);
        hasOperation = savedInstanceState.getBoolean(STATE_HAS_OPERATION);
        canNegative = savedInstanceState.getBoolean(STATE_CAN_NEGATIVE);
        canOperate = savedInstanceState.getBoolean(STATE_CAN_OPERATE);
        textViewResult.setText(savedInstanceState.getString(STATE_RESULT));
    }
}
