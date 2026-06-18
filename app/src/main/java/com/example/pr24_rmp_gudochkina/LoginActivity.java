package com.example.pr24_rmp_gudochkina;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private MaterialButton btnFurther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etEmail = findViewById(R.id.etEmail);
        btnFurther = findViewById(R.id.btnFurther);

        updateButtonState(false);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = s.toString().trim();
                boolean isValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
                updateButtonState(isValid);
            }
        });

        btnFurther.setOnClickListener(v -> {
            if (btnFurther.isEnabled()) {
                Intent intent = new Intent(LoginActivity.this, CodeConfirmationActivity.class);
                intent.putExtra("email", etEmail.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void updateButtonState(boolean isActive) {
        btnFurther.setEnabled(isActive);
        if (isActive) {
            btnFurther.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bright_blue))
            );
        } else {
            btnFurther.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_blue))
            );
        }
    }
}