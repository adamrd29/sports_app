package com.example.androidproject;

import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public class TableFragment extends Fragment {
    String apiKey = BuildConfig.API_KEY;
    private View view;
    private TextView tableStatus;

    // Interface for API call, inserts API key into header
    interface RequestTable {
        @GET("competitions/PD/standings")
        Call<TableData> getTable(@Header("X-Auth-Token") String apiKey);
    }

    public TableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table, container, false);

        tableStatus = view.findViewById(R.id.tableStatus);

        // Build retrofit call
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.football-data.org/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestTable requestTable = retrofit.create(RequestTable.class);

        // Ensure response from the server has succeeded and contains the information, failure handled accordingly
        requestTable.getTable(apiKey).enqueue(new Callback<TableData>() {
            @Override
            public void onResponse(@NonNull Call<TableData> call, @NonNull Response<TableData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tableStatus.setVisibility(View.INVISIBLE);
                    populateTable(response.body());
                } else {
                    tableStatus.setText("Failed to retrieve data, please refresh");
                    Log.e("Error", "Request failed or returned null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<TableData> call, @NonNull Throwable throwable) {
                tableStatus.setText("Failed to retrieve  data, please refresh");
                Log.e("Error", Objects.requireNonNull(throwable.getMessage()));
            }
        });

        return view;
    }

    // Starting with an initial static header row, dynamically populated table rows and adds them to the table until the for loop has been exhausted
    private void populateTable(TableData tableData) {
        TableLayout tableLayout = view.findViewById(R.id.leagueTable);
        if (tableData != null && tableData.standings != null) {
            TableRow headerRow = new TableRow(getContext());

            TextView placement = new TextView(getContext());
            placement.setText("Pos");
            placement.setTypeface(null, Typeface.BOLD);
            placement.setGravity(Gravity.CENTER);
            placement.setPadding(0, 16, 0, 0);

            ImageView crestPlaceholder = new ImageView(getContext());
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(75, 75);
            layoutParams.gravity = Gravity.CENTER;
            crestPlaceholder.setLayoutParams(layoutParams);

            TextView teamName = new TextView(getContext());
            teamName.setText("Team");
            teamName.setTypeface(null, Typeface.BOLD);
            teamName.setPadding(16, 16, 0, 0);

            TextView MP = new TextView(getContext());
            MP.setText("MP");
            MP.setTypeface(null, Typeface.BOLD);
            MP.setGravity(Gravity.CENTER);
            MP.setPadding(16, 16, 16, 0);

            TextView points = new TextView(getContext());
            points.setText("Pts");
            points.setTypeface(null, Typeface.BOLD);
            points.setGravity(Gravity.CENTER);
            points.setPadding(16, 16, 16, 0);

            TextView GD = new TextView(getContext());
            GD.setText("GD");
            GD.setTypeface(null, Typeface.BOLD);
            GD.setGravity(Gravity.CENTER);
            GD.setPadding(16, 16, 16, 0);

            TextView GF = new TextView(getContext());
            GF.setText("GF");
            GF.setTypeface(null, Typeface.BOLD);
            GF.setGravity(Gravity.CENTER);
            GF.setPadding(16, 16, 16, 0);

            TextView GA = new TextView(getContext());
            GA.setText("GA");
            GA.setTypeface(null, Typeface.BOLD);
            GA.setGravity(Gravity.CENTER);
            GA.setPadding(16, 16, 16, 0);

            headerRow.addView(placement);
            headerRow.addView(crestPlaceholder);
            headerRow.addView(teamName);
            headerRow.addView(MP);
            headerRow.addView(points);
            headerRow.addView(GD);
            headerRow.addView(GF);
            headerRow.addView(GA);
            tableLayout.addView(headerRow);

            int index = 0;
            for (TableData.Standing standing : tableData.standings) {
                if (standing.table != null) {
                    for (TableData.Table table : standing.table) {
                        index++;
                        TableRow newRow = new TableRow(getContext());
                        newRow.setPadding(0, 32, 0, 32);

                        TextView placementTextView = new TextView(getContext());
                        placementTextView.setText(String.valueOf(index));
                        placementTextView.setGravity(Gravity.CENTER);
                        placementTextView.setPadding(16, 0, 32, 0);

                        ImageView clubCrestImageView = new ImageView(getContext());
                        TableRow.LayoutParams placeholderParams = new TableRow.LayoutParams(75, 75);
                        layoutParams.gravity = Gravity.CENTER;
                        clubCrestImageView.setLayoutParams(placeholderParams);

                        // Manage alternate loading procedures for SVGs and PNGs
                        if(table.team.crest.toLowerCase().endsWith(".svg")){
                            GlideToVectorYou.justLoadImage(getActivity(), Uri.parse(table.team.crest), clubCrestImageView);
                        }else{
                            Picasso.get().load(table.team.crest).into(clubCrestImageView);
                        }

                        TextView teamTextView = new TextView(getContext());
                        teamTextView.setText(table.team.shortName);
                        teamTextView.setPadding(32, 0, 0, 0);

                        TextView MPTextView = new TextView(getContext());
                        MPTextView.setText(String.valueOf(table.playedGames));
                        MPTextView.setGravity(Gravity.CENTER);
                        MPTextView.setPadding(32, 0, 32, 0);

                        TextView pointsTextView = new TextView(getContext());
                        pointsTextView.setText(String.valueOf(table.points));
                        pointsTextView.setGravity(Gravity.CENTER);
                        pointsTextView.setPadding(32, 0, 32, 0);

                        TextView goalDifferenceTextView = new TextView(getContext());
                        goalDifferenceTextView.setText(String.valueOf(table.goalDifference));
                        goalDifferenceTextView.setGravity(Gravity.CENTER);
                        goalDifferenceTextView.setPadding(32, 0, 32, 0);

                        TextView goalsForTextView = new TextView(getContext());
                        goalsForTextView.setText(String.valueOf(table.goalsFor));
                        goalsForTextView.setGravity(Gravity.CENTER);
                        goalsForTextView.setPadding(32, 0, 32, 0);

                        TextView goalsAgainstTextView = new TextView(getContext());
                        goalsAgainstTextView.setText(String.valueOf(table.goalsAgainst));
                        goalsAgainstTextView.setGravity(Gravity.CENTER);
                        goalsAgainstTextView.setPadding(32, 0, 32, 0);

                        newRow.addView(placementTextView);
                        newRow.addView(clubCrestImageView);
                        newRow.addView(teamTextView);
                        newRow.addView(MPTextView);
                        newRow.addView(pointsTextView);
                        newRow.addView(goalDifferenceTextView);
                        newRow.addView(goalsForTextView);
                        newRow.addView(goalsAgainstTextView);

                        tableLayout.addView(newRow);
                    }
                }
            }
        }
    }
}
