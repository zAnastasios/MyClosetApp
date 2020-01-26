package com.example.myclosetapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myclosetapp.utils.FormatDateTime;

@Database(entities = Clothe.class,version = 9)
@TypeConverters({FormatDateTime.class})
public abstract class AppDatabase extends RoomDatabase {

    //This is used to create a singlenton of the DB
    private static AppDatabase instance;

    public abstract ClotheDAO clotheDao();
    //synchonized for not multiple instance creation
    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
