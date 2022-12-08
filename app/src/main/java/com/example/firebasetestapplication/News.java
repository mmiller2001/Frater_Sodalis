package com.example.firebasetestapplication;

public class News {

    String club_logo;
    String club;
    String club_description;
    String title;
    String description;
    String time;

    public News() {

    }

    public News(String club_logo, String club_description, String club, String title, String description, String time) {
        this.club_logo = club_logo;
        this.club_description = club_description;
        this.club = club;
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getClub_logo() {return club_logo;}
    public String getClub() {return club;}
    public String getClub_description() {return club_description;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public String getTime() {return time;}

    public void setClub_logo(String club_logo) {this.club_logo = club_logo;}
    public void setClub(String club) {this.club = club;}
    public void setClub_description(String club_description) {this.club_description = club_description;}
    public void setTitle(String title) {this.title = title;}
    public void setDescription(String description) {this.description = description;}
    public void setTime(String time) {this.time = time;}


}
