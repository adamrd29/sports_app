package com.example.androidproject;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class MatchFragment extends DialogFragment {

    int matchId;
    String apiKey = BuildConfig.API_KEY;
    MatchData matchData;
    ImageView homeCrest, awayCrest;
    TextView homeTeam, awayTeam, date, venue, matchStatus, status, separator, homeScore, awayScore, halfTimeScore;

    // Interface to make API call, inserts API key and match id information from variable
    interface RequestMatchData {
        @GET("matches/{id}")
        Call<MatchData> getMatchData(@Header("X-Auth-Token") String apiKey, @Path("id") String matchId);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get bundled match ID for API call
        if (getArguments() != null) {
            matchId = getArguments().getInt("id", 0);
            Log.i("id", String.valueOf(matchId));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_match, container, false);

        // Obtain views for alteration
        separator = view.findViewById(R.id.separator);
        homeCrest = view.findViewById(R.id.homeCrest2);
        awayCrest = view.findViewById(R.id.awayCrest2);
        homeTeam = view.findViewById(R.id.homeTeam2);
        awayTeam = view.findViewById(R.id.awayTeam2);
        homeScore = view.findViewById(R.id.homeScore2);
        awayScore = view.findViewById(R.id.awayScore2);
        date = view.findViewById(R.id.date2);
        venue = view.findViewById(R.id.venue);
        matchStatus = view.findViewById(R.id.matchStatus2);
        status = view.findViewById(R.id.status2);
        halfTimeScore = view.findViewById(R.id.halfTimeScore);

        // Build retrofit call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.football-data.org/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MatchFragment.RequestMatchData requestMatchData = retrofit.create(MatchFragment.RequestMatchData.class);
        requestMatchData.getMatchData(apiKey, String.valueOf(matchId)).enqueue(new Callback<MatchData>() {

            // Ensure response from the server has succeeded and contains the information, failure handled accordingly
            @Override
            public void onResponse(@NonNull Call<MatchData> call, @NonNull Response<MatchData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    matchData = response.body();

                    status.setVisibility(view.INVISIBLE);
                    separator.setVisibility(view.VISIBLE);

                    String homeCrestURL = matchData.getHomeTeam().getCrest();
                    // Manage alternate loading procedures for SVGs and PNGs
                    if(homeCrestURL.toLowerCase().endsWith(".svg")){
                        GlideToVectorYou.justLoadImage(getActivity(), Uri.parse(homeCrestURL), homeCrest);
                    }else{
                        Picasso.get().load(homeCrestURL).into(homeCrest);
                    }
                    homeCrest.setVisibility(view.VISIBLE);

                    String awayCrestURL = matchData.getAwayTeam().getCrest();
                    if(awayCrestURL.toLowerCase().endsWith(".svg")){
                        GlideToVectorYou.justLoadImage(getActivity(), Uri.parse(awayCrestURL), awayCrest);
                    }else{
                        Picasso.get().load(awayCrestURL).into(awayCrest);
                    }
                    awayCrest.setVisibility(view.VISIBLE);

                    homeTeam.setText(matchData.getHomeTeam().getShortName());
                    homeTeam.setVisibility(view.VISIBLE);

                    awayTeam.setText(matchData.getAwayTeam().getShortName());
                    awayTeam.setVisibility(view.VISIBLE);

                    homeScore.setText(String.valueOf(matchData.getScore().getFullTime().getHome()));
                    homeScore.setVisibility(view.VISIBLE);

                    awayScore.setText(String.valueOf(matchData.getScore().getFullTime().getAway()));
                    awayScore.setVisibility(view.VISIBLE);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy HH:mm");
                    date.setText(dateFormat.format(matchData.getUtcDate()));
                    date.setVisibility(view.VISIBLE);

                    venue.setText(matchData.getVenue());
                    venue.setVisibility(view.VISIBLE);

                    String halfTimeScoreText = "(" + String.valueOf(matchData.getScore().getHalfTime().getHome()) + " - " + String.valueOf(matchData.getScore().getHalfTime().getAway()) + ")";
                    halfTimeScore.setText(halfTimeScoreText);
                    halfTimeScore.setVisibility(view.VISIBLE);

                    // status used to alter the matchStatus field based on the whether the match has took place, is taking place, or is scheduled to take place
                    if(matchData.status.equals("FINISHED")){
                        matchStatus.setText("Full Time");
                    } else if(matchData.status.equals("LIVE") || matchData.status.equals("IN_PLAY")){
                        matchStatus.setText("Live");
                        matchStatus.setTextColor(Color.GREEN);
                    } else if(matchData.status.equals("TIMED")){
                        matchStatus.setText("Scheduled");
                    }
                    matchStatus.setVisibility(view.VISIBLE);


                } else {
                    status.setText("Failed to retrieve  data, please refresh");
                    Log.e("Error", "Live data request failed or returned null");
                }
            }
            @Override
            public void onFailure(@NonNull Call<MatchData> call, @NonNull Throwable throwable) {
                status.setText("Failed to retrieve  data, please refresh");
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

        return view;
    }

}