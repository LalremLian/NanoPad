package com.lazydeveloper.nanopad.utilitites;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lazydeveloper.nanopad.model.User;

@Database(entities = {User.class},version = 2)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao getDao();

    public static UserDatabase INSTANCE;

    public static UserDatabase getInstance(Context context){
        if(INSTANCE == null){

            //"UserDatabase" is the name of the database (Table name of the Database)
            INSTANCE = Room.databaseBuilder(context,UserDatabase.class,"UserDatabase")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
