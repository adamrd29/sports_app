package com.example.androidproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;



public class ResultsFragment extends Fragment implements ResultsInterface{
    String apiKey = BuildConfig.API_KEY;
    private RecyclerView recyclerView;

    private TextView resultsStatus;

    // Opens up the dialog fragment once a card press is detected, sends the match ID to facilitate the API call needed to display match info
    @Override
    public void onResultClick(int id) {
        MatchFragment matchFragment = new MatchFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        matchFragment.setArguments(bundle);

        matchFragment.show(requireActivity().getSupportFragmentManager(), "MatchFragment");
    }

    // Interface for API call
    interface RequestResults {
        @GET("competitions/PD/matches?status=FINISHED")
        Call<ResultsData> getResults(@Header("X-Auth-Token") String apiKey);
    }

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.matchesRecyclerView);
        TextView resultsStatus = view.findViewById(R.id.resultsStatus);

        // Construct the retrofit call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.football-data.org/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestResults requestResults = retrofit.create(RequestResults.class);

        requestResults.getResults(apiKey).enqueue(new Callback<ResultsData>() {
            // Ensure response from the server has succeeded and contains the information, failure handled accordingly
            @Override
            public void onResponse(@NonNull Call<ResultsData> call, @NonNull Response<ResultsData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<ResultsData.Match> matches = response.body().getMatches();
                    ResultsAdapter adapter = new ResultsAdapter(getContext(), matches, ResultsFragment.this, getActivity());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    resultsStatus.setVisibility(view.INVISIBLE);
                } else {
                    resultsStatus.setText("Failed to retrieve data, please refresh");
                    Log.e("Error", "Request failed or returned null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResultsData> call, @NonNull Throwable throwable) {
                resultsStatus.setText("Failed to retrieve data, please refresh");
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

}