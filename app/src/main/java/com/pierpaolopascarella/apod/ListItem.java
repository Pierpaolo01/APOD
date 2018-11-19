package com.pierpaolopascarella.apod;

public class ListItem {

    private String title;
    private String text;
    private String date;
    private String pictureURL;





    public ListItem(String title, String text, String date, String pictureURL) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.pictureURL = pictureURL;


    }

    public String getTitle() {

        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getPictureURL() {
        return pictureURL;
    }

}
