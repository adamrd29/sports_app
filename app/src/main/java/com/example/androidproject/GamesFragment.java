package com.example.androidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;



public class GamesFragment extends Fragment implements ResultsInterface{
    String apiKey = BuildConfig.API_KEY;
    private RecyclerView recyclerView;
    private TextView gamesStatus;

    // Opens up the dialog fragment once a card press is detected, sends the match ID to facilitate the API call needed to display match info
    @Override
    public void onResultClick(int id) {
        MatchFragment matchFragment = new MatchFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        matchFragment.setArguments(bundle);

        matchFragment.show(requireActivity().getSupportFragmentManager(), "MatchFragment");
    }

    // Interfaces for API calls
    interface LiveResults {
        @GET("competitions/PD/matches?status=LIVE")
        Call<ResultsData> getResults(@Header("X-Auth-Token") String apiKey);
    }
    interface ScheduledResults {
        @GET("competitions/PD/matches?status=SCHEDULED")
        Call<ResultsData> getResults(@Header("X-Auth-Token") String apiKey);
    }

    public GamesFragment() {
        // Required empty public constructor
    }

    private ResultsData liveData;
    private ResultsData scheduledData;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Construct the retrofit call
        recyclerView = view.findViewById(R.id.matchesRecyclerView);
        gamesStatus = view.findViewById(R.id.gamesStatus);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.football-data.org/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LiveResults liveResults = retrofit.create(LiveResults.class);
        liveResults.getResults(apiKey).enqueue(new Callback<ResultsData>() {
            // Ensure response from the server has succeeded and contains the information, failure handled accordingly
            @Override
            public void onResponse(@NonNull Call<ResultsData> call, @NonNull Response<ResultsData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData = response.body();
                    createAdapter(view);
                } else {
                    gamesStatus.setText("Failed to load data, please refresh");
                    Log.e("Error", "Live data request failed or returned null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultsData> call, @NonNull Throwable throwable) {
                gamesStatus.setText("Failed to load data, please refresh");
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

        // Make the second API call
        ScheduledResults scheduledResults = retrofit.create(ScheduledResults.class);
        scheduledResults.getResults(apiKey).enqueue(new Callback<ResultsData>() {
            @Override
            public void onResponse(@NonNull Call<ResultsData> call, @NonNull Response<ResultsData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    scheduledData = response.body();
                    createAdapter(view);
                } else {
                    gamesStatus.setText("Failed to load data, please refresh");
                    Log.e("Error", "Scheduled data request failed or returned null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultsData> call, @NonNull Throwable throwable) {
                gamesStatus.setText("Failed to load data, please refresh");
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    // Function to ensure that all data is present to prevent a null pointer error
    private void createAdapter(View view) {
        if (liveData != null && scheduledData != null) {
            if(liveData.getMatches().isEmpty() && scheduledData.getMatches().isEmpty()){
                gamesStatus.setText("No upcoming games available");
            }else{
                gamesStatus.setVisibility(view.INVISIBLE);
                GamesAdapter adapter = new GamesAdapter(getContext(), liveData, scheduledData, GamesFragment.this, getActivity());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    }
}
