package com.example.asher.mytestapplication.model;

/**
 * Created by Asher on 16-Jun-15.
 */
public class NavDrawerItem {

    private String title;
    private String sub_title;
    private int icon;


    public NavDrawerItem(String title, String sub_title, int icon){
        this.title = title;
        this.sub_title = sub_title;
        this.icon = icon;
    }

    public String get_title(){return title;}
    public void set_title(String title){this.title = title;}

    public String get_sub_title(){return sub_title;}
    public void set_sub_title(String sub_title){this.sub_title = sub_title;}

    public int get_icon(){return icon;}
    public void set_icon(int icon){this.icon = icon;}

}
