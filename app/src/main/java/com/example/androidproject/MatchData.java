package com.example.androidproject;

import java.util.ArrayList;
import java.util.Date;

public class MatchData {
    public Date utcDate;
    public String status;
    public String venue;
    public int matchday;
    public HomeTeam homeTeam;
    public AwayTeam awayTeam;
    public Score score;
    public ArrayList<Referee> referees;

    public Date getUtcDate() {
        return utcDate;
    }

    public void setUtcDate(Date utcDate) {
        this.utcDate = utcDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getMatchday() {
        return matchday;
    }

    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

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

    public ArrayList<Referee> getReferees() {
        return referees;
    }

    public void setReferees(ArrayList<Referee> referees) {
        this.referees = referees;
    }

    public class AwayTeam{
        public int id;
        public String name;
        public String shortName;
        public String tla;
        public String crest;

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
    }

    public class FullTime{
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

    public class HalfTime{
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

    public class HomeTeam{
        public int id;
        public String name;
        public String shortName;
        public String tla;
        public String crest;

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
    }

    public class Referee{
        public int id;
        public String name;
        public String type;
        public String nationality;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }
    }

    public class Score{
        public String winner;
        public String duration;
        public FullTime fullTime;
        public HalfTime halfTime;

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public FullTime getFullTime() {
            return fullTime;
        }

        public void setFullTime(FullTime fullTime) {
            this.fullTime = fullTime;
        }

        public HalfTime getHalfTime() {
            return halfTime;
        }

        public void setHalfTime(HalfTime halfTime) {
            this.halfTime = halfTime;
        }
    }
}
