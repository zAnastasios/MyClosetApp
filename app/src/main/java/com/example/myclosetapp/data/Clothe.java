package com.example.myclosetapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.myclosetapp.utils.FormatDateTime;

import java.util.Date;

@Entity(tableName = "clothe_table")
public class Clothe {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int clohteid=0;

    @NonNull
    private String color;
    @NonNull
    private String category;

    private String description;


    @NonNull
    @TypeConverters(FormatDateTime.class)
    private Date dateLastWorn;

    @NonNull
    private Boolean toClean;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;



    public Clothe( @NonNull String color, @NonNull String category, String description, @NonNull Boolean toClean,@NonNull byte[] image,@NonNull Date dateLastWorn) {
        this.color = color;
        this.category = category;
        this.description = description;
        this.toClean = toClean;
        this.image=image;
        this.dateLastWorn=dateLastWorn;
    }

    public void setClohteid(int clohteid) {
        this.clohteid = clohteid;
    }

    public int getClohteid() {
        return clohteid;
    }

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

//    public Date getLastworn() {
//        return lastworn;
//    }
    public byte[] getImage() {
      return image;
    }

    public Boolean getToClean() {
        return toClean;
    }

    public void setColor(@NonNull String color) {
        this.color = color;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public Date getDateLastWorn() {
        return dateLastWorn;
    }

    public void setDateLastWorn(@NonNull Date dateLastWorn) {
        this.dateLastWorn = dateLastWorn;
    }

    public void setToClean(@NonNull Boolean toClean) {
        this.toClean = toClean;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
