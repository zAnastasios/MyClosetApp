package com.example.myclosetapp.utils;



import androidx.room.TypeConverter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateTime {

    static DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
    @TypeConverter
    public static Date fromTimestampToDate(String value) {

        try {
            return  dateFormatter.parse(value) ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @TypeConverter
    public static String dateToTimestamp(Date date) {
      // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return date == null ? null : dateFormatter.format(date);
    }




}
