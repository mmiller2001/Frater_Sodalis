package com.example.firebasetestapplication;

public class News {

    String club;
    String club_description;
    String title;
    String description;
    String time;

    public News(String club, String club_description, String title, String description, String time) {
        this.club = club;
        this.club_description = club_description;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getClub() {return club;}
    public String getClub_description() {return club_description;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getTime() {return time;}

    public void setClub(String club) {this.club = club;}
    public void setClub_description(String club_description) {this.club_description = club_description;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setTime(String time) {this.time = time;}


}
