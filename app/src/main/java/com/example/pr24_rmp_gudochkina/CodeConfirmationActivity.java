package com.example.pr24_rmp_gudochkina;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CodeConfirmationActivity extends AppCompatActivity {

    private EditText[] codeDigits;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_code_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        codeDigits = new EditText[]{
                findViewById(R.id.codeDigit1),
                findViewById(R.id.codeDigit2),
                findViewById(R.id.codeDigit3),
                findViewById(R.id.codeDigit4)
        };

        for (int i = 0; i < codeDigits.length; i++) {
            final int position = i;
            codeDigits[i].setOnClickListener(v -> {
                currentIndex = position;
            });
        }

        setupDigitButton(R.id.btn1, "1");
        setupDigitButton(R.id.btn2, "2");
        setupDigitButton(R.id.btn3, "3");
        setupDigitButton(R.id.btn4, "4");
        setupDigitButton(R.id.btn5, "5");
        setupDigitButton(R.id.btn6, "6");
        setupDigitButton(R.id.btn7, "7");
        setupDigitButton(R.id.btn8, "8");
        setupDigitButton(R.id.btn9, "9");
        setupDigitButton(R.id.btn0, "0");

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> deleteLastDigit());

    }

    private void setupDigitButton(int buttonId, String digit) {
        Button btn = findViewById(buttonId);
        btn.setOnClickListener(v -> enterDigit(digit));
    }

    private void enterDigit(String digit) {
        codeDigits[currentIndex].setText(digit);

        if (currentIndex < codeDigits.length - 1) {
            currentIndex++;
        } else {
            goToNextScreen();
        }
    }

    private void deleteLastDigit() {
        if (codeDigits[currentIndex].getText().length() > 0) {
            codeDigits[currentIndex].setText("");
        } else if (currentIndex > 0) {
            currentIndex--;
            codeDigits[currentIndex].setText("");
        }
    }


    private void goToNextScreen() {
        Intent intent = new Intent(CodeConfirmationActivity.this, CreatingPasswordActivity.class);
        startActivity(intent);
    }

}