package com.example.assigment_walmart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Adapter_Country countryAdapter;
    private CountryViewModel countryViewModel;
    private ProgressBar progressBar;
    private Button retryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressBar);
        retryButton = findViewById(R.id.retryButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countryAdapter = new Adapter_Country();
        recyclerView.setAdapter(countryAdapter);

        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        countryViewModel.getCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                progressBar.setVisibility(View.GONE);
                if (countries != null && !countries.isEmpty()) {
                    retryButton.setVisibility(View.GONE);
                    countryAdapter.setCountries(countries);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load countries from API", Toast.LENGTH_SHORT).show();
                }
            }
        });
        countryViewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    progressBar.setVisibility(View.GONE);
                    retryButton.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                countryAdapter.setCountries(new ArrayList<>());
                countryViewModel.fetch_countries();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countryViewModel.getCountries().removeObservers(this);
        countryViewModel.getErrorMessage().removeObservers(this);
    }
}