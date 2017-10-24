/**
  * Copyright 2017 bejson.com 
  */
package com.alcatraz.biligrabdemo.bean;
import java.util.List;

/**
 * Auto-generated: 2017-10-22 22:11:11
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Media {

    private List<Area> area;
    private String cover;
    private Episode_index episode_index;
    private int media_id;
    private Publish publish;
    private Rating rating;
    private String title;
    private int type_id;
    private String type_name;
    public void setArea(List<Area> area) {
         this.area = area;
     }
     public List<Area> getArea() {
         return area;
     }

    public void setCover(String cover) {
         this.cover = cover;
     }
     public String getCover() {
         return cover;
     }

    public void setEpisode_index(Episode_index episode_index) {
         this.episode_index = episode_index;
     }
     public Episode_index getEpisode_index() {
         return episode_index;
     }

    public void setMedia_id(int media_id) {
         this.media_id = media_id;
     }
     public int getMedia_id() {
         return media_id;
     }

    public void setPublish(Publish publish) {
         this.publish = publish;
     }
     public Publish getPublish() {
         return publish;
     }

    public void setRating(Rating rating) {
         this.rating = rating;
     }
     public Rating getRating() {
         return rating;
     }

    public void setTitle(String title) {
         this.title = title;
     }
     public String getTitle() {
         return title;
     }

    public void setType_id(int type_id) {
         this.type_id = type_id;
     }
     public int getType_id() {
         return type_id;
     }

    public void setType_name(String type_name) {
         this.type_name = type_name;
     }
     public String getType_name() {
         return type_name;
     }

}