package com.example.androidproject;
import java.util.ArrayList;

public class TableData {
    public ArrayList<Standing> standings;

    public ArrayList<Standing> getStandings() {
        return standings;
    }

    public void setStandings(ArrayList<Standing> standings) {
        this.standings = standings;
    }

    public class Standing{
        public String stage;
        public String type;
        public Object group;
        public ArrayList<Table> table;

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getGroup() {
            return group;
        }

        public void setGroup(Object group) {
            this.group = group;
        }

        public ArrayList<Table> getTable() {
            return table;
        }

        public void setTable(ArrayList<Table> table) {
            this.table = table;
        }
    }

    public class Table{
        public int position;
        public Team team;
        public int playedGames;
        public String form;
        public int won;
        public int draw;
        public int lost;
        public int points;
        public int goalsFor;
        public int goalsAgainst;
        public int goalDifference;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }

        public int getPlayedGames() {
            return playedGames;
        }

        public void setPlayedGames(int playedGames) {
            this.playedGames = playedGames;
        }

        public String getForm() {
            return form;
        }

        public void setForm(String form) {
            this.form = form;
        }

        public int getWon() {
            return won;
        }

        public void setWon(int won) {
            this.won = won;
        }

        public int getDraw() {
            return draw;
        }

        public void setDraw(int draw) {
            this.draw = draw;
        }

        public int getLost() {
            return lost;
        }

        public void setLost(int lost) {
            this.lost = lost;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getGoalsFor() {
            return goalsFor;
        }

        public void setGoalsFor(int goalsFor) {
            this.goalsFor = goalsFor;
        }

        public int getGoalsAgainst() {
            return goalsAgainst;
        }

        public void setGoalsAgainst(int goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
        }

        public int getGoalDifference() {
            return goalDifference;
        }

        public void setGoalDifference(int goalDifference) {
            this.goalDifference = goalDifference;
        }
    }

    public class Team{
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


}
