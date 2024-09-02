package com.example.androidproject;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.MyViewHolder>{
    private final ResultsInterface resultsInterface;
    Context context;
    ArrayList<ResultsData.Match> resultsData;
    Activity activity;

    // Constructor to initialise local variables with the passed-in arguments
    // also reverses results data so latest information is displayed first
    public ResultsAdapter(Context context, ArrayList<ResultsData.Match> resultsData, ResultsInterface resultsInterface, Activity activity){
        this.context = context;
        this.resultsData = resultsData;
        Collections.reverse(resultsData);
        this.resultsInterface = resultsInterface;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.result_card, parent, false);
        return new MyViewHolder(view, resultsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResultsData.Match match = resultsData.get(position);
        holder.id = match.getId();

        // Manage alternate loading procedures for SVGs and PNGs
        if(match.getHomeTeam().getCrest().toLowerCase().endsWith(".svg")){
            GlideToVectorYou.justLoadImage(activity, Uri.parse(match.getHomeTeam().getCrest().toLowerCase()), holder.homeCrest);
        }else{
            Picasso.get().load(match.getHomeTeam().getCrest()).into(holder.homeCrest);
        }
        holder.homeTeam.setText(match.getHomeTeam().getShortName());
        holder.homeScore.setText(String.valueOf(match.getScore().getFullTime().getHome()));

        if(match.getAwayTeam().getCrest().toLowerCase().endsWith(".svg")){
            GlideToVectorYou.justLoadImage(activity, Uri.parse(match.getAwayTeam().getCrest().toLowerCase()), holder.awayCrest);
        }else{
            Picasso.get().load(match.getAwayTeam().getCrest()).into(holder.awayCrest);
        }

        holder.awayTeam.setText(match.getAwayTeam().getShortName());
        holder.awayScore.setText(String.valueOf(match.getScore().getFullTime().getAway()));

        // Date Formatting from UTC to remove time element
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = date.format(match.getUtcDate());
        holder.date.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return resultsData.size();
    } // Dynamically obtain dataset size

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView homeCrest, awayCrest;
        TextView homeTeam, homeScore, awayTeam, awayScore, date;
        int id;
        public MyViewHolder(@NonNull View itemView, ResultsInterface resultsInterface) {
            super(itemView);

            // Obtain textViews that will be altered
            homeCrest = itemView.findViewById(R.id.homeCrest);
            awayCrest = itemView.findViewById(R.id.awayCrest);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            homeScore = itemView.findViewById(R.id.homeScore);
            awayScore = itemView.findViewById(R.id.awayScore);
            date = itemView.findViewById(R.id.date);

            //Setup listener to detect card presses, provides match ID on click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(resultsInterface != null){
                        resultsInterface.onResultClick(id);
                    }
                }
            });
        }
    }
}
