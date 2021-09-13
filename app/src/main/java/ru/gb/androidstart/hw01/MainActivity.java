package ru.gb.androidstart.hw01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button buttonGuess;
    private Button buttonNew;
    private Button buttonNextPage;
    private EditText editTextGuess;
    private TextView textViewGuess;
    private TextView textViewNumber;
    private SwitchCompat switchShowNumber;
    private ToggleButton toggleButtonShowCheckBox;
    private CheckBox checkBoxEasyWin;
    private TextView textViewRes;
    private int[] arrNumber = new int[7];
    private StringBuilder strNumber = new StringBuilder("");
    private StringBuilder strHiddenNumber = new StringBuilder("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateNumber();

        strHiddenNumber.append(getString(R.string.text_view_hidden_text));
        String strWin = getString(R.string.text_view_result_win);
        String stfFail = getString(R.string.text_view_result_fail);
        String strDefault = getString(R.string.text_view_result_default);
        editTextGuess = findViewById(R.id.edit_text_01);
        textViewRes = findViewById(R.id.text_view_res);
        buttonGuess = findViewById(R.id.button_guess);
        textViewGuess = findViewById(R.id.text_view_guess);
        textViewGuess.requestFocus();

        //show generated number
        switchShowNumber = findViewById(R.id.switch_compat);
        textViewNumber = findViewById(R.id.text_view_number);
        textViewNumber.setText(strHiddenNumber);
        switchShowNumber.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                textViewNumber.setText(strNumber.toString());
            }
            else{
                textViewNumber.setText(strHiddenNumber);
            }
        });

        //show check box
        toggleButtonShowCheckBox = findViewById(R.id.toggle_button);
        checkBoxEasyWin = findViewById(R.id.check_box);
        toggleButtonShowCheckBox.setOnClickListener(v -> {
            boolean isChecked = ((ToggleButton) v).isChecked();
            if (isChecked){
                checkBoxEasyWin.setVisibility(View.VISIBLE);
            }
            else{
                checkBoxEasyWin.setVisibility(View.INVISIBLE);
            }
        });

        /*
        player have to guess generated number
        full win - all 7 digits is guessed
        easy win - at least 1 digit is guessed
        by default generated number is hidden and only guessed digits are shown
         */
        buttonGuess.setOnClickListener(v -> {
            String strGuess = editTextGuess.getText().toString();
            StringBuilder strGuessToShow = new StringBuilder("");
            boolean isFullWin = true;
            boolean isEasyWin = false;
            if (strGuess.length() < 7) {
                Toast.makeText(this, "Введите 7 цифр", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < arrNumber.length; i++) {
                    strGuessToShow.append(strGuess.charAt(i));
                    if (i < arrNumber.length - 1) {
                        strGuessToShow.append("  ");
                    }
                }
                textViewGuess.setText(strGuessToShow.toString());
                strHiddenNumber.setLength(0);
                int k;
                for (int i = 0; i < arrNumber.length; i++) {
                    k = Character.getNumericValue(strGuess.charAt(i));
                    if (arrNumber[i] == k) {
                        strHiddenNumber.append(arrNumber[i]);
                        isEasyWin = true;
                    }
                    else {
                        strHiddenNumber.append("*");
                        isFullWin = false;
                    }
                    if (i < arrNumber.length - 1) {
                        strHiddenNumber.append("  ");
                    }
                }
                textViewNumber.setText(strHiddenNumber.toString());
            }
            if (strGuess.length() == 7) {
                if (isFullWin || (checkBoxEasyWin.isChecked() && isEasyWin)) {
                    textViewRes.setText(strWin);
                } else {
                    textViewRes.setText(stfFail);
                }
            }
        });

        //restart game
        buttonNew = findViewById(R.id.button_new);
        buttonNew.setOnClickListener(v -> {
            generateNumber();
            strHiddenNumber.setLength(0);
            strHiddenNumber.append(getString(R.string.text_view_hidden_text));
            textViewNumber.setText(strHiddenNumber.toString());
            editTextGuess.getText().clear();
            if (switchShowNumber.isChecked()) {
                switchShowNumber.performClick();
            }
            if (checkBoxEasyWin.isChecked()) {
                checkBoxEasyWin.setChecked(false);
            }
            if (toggleButtonShowCheckBox.isChecked()) {
                toggleButtonShowCheckBox.performClick();
            }
            textViewGuess.setText("");
            textViewRes.setText(strDefault);
        });

        buttonNextPage = findViewById(R.id.button_next_page);
        buttonNextPage.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }

    protected void generateNumber(){
        strNumber.setLength(0);
        Random rand = new Random();
        for (int i = 0; i < arrNumber.length; i++) {
            arrNumber[i] = rand.nextInt(10);
            strNumber.append(arrNumber[i]);
            if (i < arrNumber.length - 1) {
                strNumber.append("  ");
            }
        }
    }

}