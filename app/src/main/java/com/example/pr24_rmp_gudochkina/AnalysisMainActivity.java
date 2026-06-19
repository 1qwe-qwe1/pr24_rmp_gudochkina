package com.example.pr24_rmp_gudochkina;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class AnalysisMainActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btnPopular, btnCovid, btnComprehensive;
    private LinearLayout cardsContainer;
    private Button activeCategoryButton;

    private List<AnalysisItem> allAnalyses;
    private List<AnalysisItem> filteredAnalyses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        etSearch = findViewById(R.id.etSearch);
        btnPopular = findViewById(R.id.btnPopular);
        btnCovid = findViewById(R.id.btnCovid);
        btnComprehensive = findViewById(R.id.btnComprehensive);
        cardsContainer = findViewById(R.id.cardsContainer);

        loadData();

        activeCategoryButton = btnPopular;
        setupCategories();

        setupSearch();

        displayCards(allAnalyses);
    }

    private void loadData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        allAnalyses = new ArrayList<>();

        db.collection("analyses")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String title = document.getString("title");
                            String days = document.getString("days");
                            String price = document.getString("price");
                            String category = document.getString("category");

                            allAnalyses.add(new AnalysisItem(title, days, price, category));
                        }
                        filterAndDisplay("popular");
                    } else {
                        Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setupCategories() {
        btnPopular.setOnClickListener(v -> selectCategory(btnPopular, "popular"));
        btnCovid.setOnClickListener(v -> selectCategory(btnCovid, "covid"));
        btnComprehensive.setOnClickListener(v -> selectCategory(btnComprehensive, "comprehensive"));
    }

    private void selectCategory(Button selectedButton, String category) {
        activeCategoryButton.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.light_blue)
        );
        activeCategoryButton.setTextColor(ContextCompat.getColor(this, R.color.black));

        selectedButton.setBackgroundTintList(
                ContextCompat.getColorStateList(this, R.color.bright_blue)
        );
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.white));

        activeCategoryButton = selectedButton;

        filterAndDisplay(category);
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim().toLowerCase();
                filterBySearch(query);
            }
        });
    }

    private void filterAndDisplay(String category) {
        filteredAnalyses = new ArrayList<>();
        for (AnalysisItem item : allAnalyses) {
            if (item.category.equals(category)) {
                filteredAnalyses.add(item);
            }
        }
        displayCards(filteredAnalyses);
    }

    private void filterBySearch(String query) {
        if (query.isEmpty()) {
            String currentCategory = getCategoryFromButton(activeCategoryButton);
            filterAndDisplay(currentCategory);
        } else {
            List<AnalysisItem> searchResults = new ArrayList<>();
            for (AnalysisItem item : allAnalyses) {
                if (item.title.toLowerCase().contains(query)) {
                    searchResults.add(item);
                }
            }
            displayCards(searchResults);
        }
    }

    private String getCategoryFromButton(Button button) {
        if (button == btnPopular) return "popular";
        if (button == btnCovid) return "covid";
        if (button == btnComprehensive) return "comprehensive";
        return "popular";
    }

    private void displayCards(List<AnalysisItem> items) {
        cardsContainer.removeAllViews();

        for (AnalysisItem item : items) {
            View cardView = LayoutInflater.from(this).inflate(R.layout.item_analysis_card, cardsContainer, false);

            TextView tvTitle = cardView.findViewById(R.id.tvTitle);
            TextView tvDays = cardView.findViewById(R.id.tvDays);
            TextView tvPrice = cardView.findViewById(R.id.tvPrice);
            Button btnAdd = cardView.findViewById(R.id.btnAdd);

            tvTitle.setText(item.title);
            tvDays.setText(item.days);
            tvPrice.setText(item.price);

            btnAdd.setOnClickListener(v -> {
                btnAdd.setText("Добавлено");
                btnAdd.setEnabled(false);
            });

            cardsContainer.addView(cardView);
        }
    }

    private static class AnalysisItem {
        String title;
        String days;
        String price;
        String category;

        AnalysisItem(String title, String days, String price, String category) {
            this.title = title;
            this.days = days;
            this.price = price;
            this.category = category;
        }
    }
}