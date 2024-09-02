package com.example.androidproject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder>{
    private final ResultsInterface resultsInterface;
    private final ResultsData liveData;
    private final ResultsData scheduledData;
    Context context;
    Activity activity;

    // Constructor to initialise local variables with the passed-in arguments
    public GamesAdapter(Context context, ResultsData liveData, ResultsData scheduledData, ResultsInterface resultsInterface, Activity activity){
        this.context = context;
        this.liveData = liveData;
        this.scheduledData = scheduledData;
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
        ResultsData.Match match;
        String status;

        // Differentiate between what data is being iterated through to allow for unique properties
        if (position < liveData.getMatches().size()) {
            match = liveData.getMatches().get(position);
            status = "LIVE";
        } else {
            int scheduledPosition = position - liveData.getMatches().size();
            match = scheduledData.getMatches().get(scheduledPosition);
            status = "SCHEDULED";
        }

        holder.id = match.getId();
        holder.status = status;

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

        // Conversion from UTC date to string
        // Removed time/ time zone information
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
        String formattedDate = date.format(match.getUtcDate());
        holder.date.setText(formattedDate);

        // Status used to influence the time text field
        if(status.equals("SCHEDULED")){
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");
            String formattedTime = time.format(match.getUtcDate());
            holder.time.setText(formattedTime);
        }else{
            holder.time.setText("LIVE");
            holder.time.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return liveData.getMatches().size() + scheduledData.getMatches().size(); // Get combined size of live data and scheduled data
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView homeCrest, awayCrest;
        TextView homeTeam, homeScore, awayTeam, awayScore, date, time;
        int id;
        String status;
        public MyViewHolder(@NonNull View itemView, ResultsInterface resultsInterface) {
            super(itemView);

            // Obtain textViews to alter
            homeCrest = itemView.findViewById(R.id.homeCrest);
            awayCrest = itemView.findViewById(R.id.awayCrest);
            homeTeam = itemView.findViewById(R.id.homeTeam);
            awayTeam = itemView.findViewById(R.id.awayTeam);
            homeScore = itemView.findViewById(R.id.homeScore);
            awayScore = itemView.findViewById(R.id.awayScore);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);

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
