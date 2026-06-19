package com.example.pr24_rmp_gudochkina;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.google.android.material.button.MaterialButton;

public class CreatingCardActivity extends AppCompatActivity {

    private EditText etName, etSurname, etPatronymic, etDateOfBirth;
    private Spinner spinnerGender;
    private MaterialButton btnCreate;
    private boolean isGenderSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_creating_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPatronymic = findViewById(R.id.etPatronymic);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnCreate = findViewById(R.id.btnCreate);

        setupGenderSpinner();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                checkAllFieldsFilled();
            }
        };

        etName.addTextChangedListener(textWatcher);
        etSurname.addTextChangedListener(textWatcher);
        etPatronymic.addTextChangedListener(textWatcher);
        etDateOfBirth.addTextChangedListener(textWatcher);

        updateButtonState(false);

        btnCreate.setOnClickListener(v -> {
            if (btnCreate.isEnabled()) {
                Intent intent = new Intent(CreatingCardActivity.this, AnalysisMainActivity.class);
                startActivity(intent);
            }
        });

        TextView buttonSkip = findViewById(R.id.buttonSkip);
        buttonSkip.setOnClickListener(v -> {
            Intent intent = new Intent(CreatingCardActivity.this, AnalysisMainActivity.class);
            startActivity(intent);
        });

    }

    private void setupGenderSpinner() {
        String[] genderOptions = new String[]{
                getString(R.string.gender),
                getString(R.string.male),
                getString(R.string.female)
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                genderOptions
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isGenderSelected = false;
                    if (view != null) {
                        ((TextView) view).setTextColor(ContextCompat.getColor(CreatingCardActivity.this, R.color.grey));
                    }
                } else {
                    isGenderSelected = true;
                    if (view != null) {
                        ((TextView) view).setTextColor(ContextCompat.getColor(CreatingCardActivity.this, R.color.black));
                    }
                }
                checkAllFieldsFilled();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                isGenderSelected = false;
                checkAllFieldsFilled();
            }
        });
    }

    private void checkAllFieldsFilled() {
        boolean isNameFilled = !etName.getText().toString().trim().isEmpty();
        boolean isSurnameFilled = !etSurname.getText().toString().trim().isEmpty();
        boolean isPatronymicFilled = !etPatronymic.getText().toString().trim().isEmpty();
        boolean isDateFilled = !etDateOfBirth.getText().toString().trim().isEmpty();

        boolean allFilled = isNameFilled && isSurnameFilled && isPatronymicFilled
                && isDateFilled && isGenderSelected;

        updateButtonState(allFilled);
    }

    private void updateButtonState(boolean isActive) {
        btnCreate.setEnabled(isActive);
        if (isActive) {
            btnCreate.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.bright_blue))
            );
        } else {
            btnCreate.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_blue))
            );
        }
    }
}