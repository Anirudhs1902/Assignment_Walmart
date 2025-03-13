package com.example.assigment_walmart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryViewModel extends ViewModel {

    private final MutableLiveData<List<Country>> countries_list= new MutableLiveData<>();
    private final MutableLiveData<String> error_msg= new MutableLiveData<>();

    public CountryViewModel() {
        fetch_countries();
    }

    public LiveData<List<Country>> getCountries() {
        return countries_list;
    }

    public LiveData<String> getErrorMessage() {
        return error_msg;
    }

    void fetch_countries() {
        Api_sorurce apiSorurce = RetrofitClient.getRetrofitInstance().create(Api_sorurce.class);
        apiSorurce.getCountries().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    countries_list.setValue(response.body());
                    error_msg.setValue(null); // Reset error message when data is loaded
                } else {
                    String error_src = "Unknown error occurred";
                    if (response.code() == 404) {
                        error_src = "Error 404: Data not found!";
                    } else if (response.code() == 500) {
                        error_src = "Server error! Try again later.";
                    } else if (response.message() != null) {
                        error_src = response.message();
                    }

                    Log.e("CountryViewModel", "Response failed: " + response.code() + " - " + error_src);
                    error_msg.setValue("Failed to load data. " + error_src);
                }
            }


            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e("CountryViewModel", "API Request Failed: " + t.getMessage());
                error_msg.setValue("Network error! Please check your Internet connection.");
            }
        });
    }
}
