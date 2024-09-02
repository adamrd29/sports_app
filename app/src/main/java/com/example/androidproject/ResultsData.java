package com.example.androidproject;

import java.util.ArrayList;
import java.util.Date;

public class ResultsData {
    public ArrayList<Match> matches;

    public Filters filters;

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public class Filters{
        public ArrayList<String> status;

        public ArrayList<String> getStatus() {
            return status;
        }

        public void setStatus(ArrayList<String> status) {
            this.status = status;
        }
    }

    public class AwayTeam {
        public int id;

        public String name;
        public String shortName;
        public String tla;
        public String crest;
        public int leagueRank;
        public String formation;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getTla() {
            return tla;
        }

        public void setTla(String tla) {
            this.tla = tla;
        }

        public String getCrest() {
            return crest;
        }

        public void setCrest(String crest) {
            this.crest = crest;
        }

        public int getLeagueRank() {
            return leagueRank;
        }

        public void setLeagueRank(int leagueRank) {
            this.leagueRank = leagueRank;
        }

        public String getFormation() {
            return formation;
        }

        public void setFormation(String formation) {
            this.formation = formation;
        }
    }

    public class FullTime {
        public int home;
        public int away;

        public int getHome() {
            return home;
        }

        public void setHome(int home) {
            this.home = home;
        }

        public int getAway() {
            return away;
        }

        public void setAway(int away) {
            this.away = away;
        }
    }

    public class HomeTeam {
        public int id;
        public String name;
        public String shortName;
        public String tla;
        public String crest;
        public int leagueRank;
        public String formation;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getTla() {
            return tla;
        }

        public void setTla(String tla) {
            this.tla = tla;
        }

        public String getCrest() {
            return crest;
        }

        public void setCrest(String crest) {
            this.crest = crest;
        }

        public int getLeagueRank() {
            return leagueRank;
        }

        public void setLeagueRank(int leagueRank) {
            this.leagueRank = leagueRank;
        }

        public String getFormation() {
            return formation;
        }

        public void setFormation(String formation) {
            this.formation = formation;
        }
    }

    public class Match {
        public HomeTeam homeTeam;
        public AwayTeam awayTeam;
        public Score score;

        public int id;
        public Date utcDate;

        public HomeTeam getHomeTeam() {
            return homeTeam;
        }

        public void setHomeTeam(HomeTeam homeTeam) {
            this.homeTeam = homeTeam;
        }

        public AwayTeam getAwayTeam() {
            return awayTeam;
        }

        public void setAwayTeam(AwayTeam awayTeam) {
            this.awayTeam = awayTeam;
        }

        public Score getScore() {
            return score;
        }

        public void setScore(Score score) {
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getUtcDate() {
            return utcDate;
        }

        public void setUtcDate(Date utcDate) {
            this.utcDate = utcDate;
        }
    }

    public class Score {
        public FullTime fullTime;

        public FullTime getFullTime() {
            return fullTime;
        }

        public void setFullTime(FullTime fullTime) {
            this.fullTime = fullTime;
        }
    }

}
