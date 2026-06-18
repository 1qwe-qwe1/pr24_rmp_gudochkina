package com.example.pr24_rmp_gudochkina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreatingPasswordActivity extends AppCompatActivity {

    private View[] dots;
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creating_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dots = new View[]{
                findViewById(R.id.dotNum1),
                findViewById(R.id.dotNum2),
                findViewById(R.id.dotNum3),
                findViewById(R.id.dotNum4)
        };

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

        TextView buttonSkip = findViewById(R.id.buttonSkip);
        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(CreatingPasswordActivity.this, CreatingCardActivity.class);
            startActivity(intent);
        });

        updateDots();
    }

    private void setupDigitButton(int buttonId, String digit) {
        Button btn = findViewById(buttonId);
        btn.setOnClickListener(v -> enterDigit(digit));
    }

    private void enterDigit(String digit) {
        if (currentIndex < dots.length) {
            currentIndex++;
            updateDots();

            if (currentIndex == dots.length) {
                goToNextScreen();
            }
        }
    }

    private void deleteLastDigit() {
        if (currentIndex > 0) {
            currentIndex--;
            updateDots();
        }
    }

    private void updateDots() {
        for (int i = 0; i < dots.length; i++) {
            if (i < currentIndex) {
                dots[i].setBackgroundResource(R.drawable.dot_filled);
            } else {
                dots[i].setBackgroundResource(R.drawable.dot_outline);
            }
        }
    }

    private void goToNextScreen() {
        dots[3].postDelayed(() -> {
            Intent intent = new Intent(CreatingPasswordActivity.this, CreatingCardActivity.class);
            startActivity(intent);
        }, 200);
    }
}