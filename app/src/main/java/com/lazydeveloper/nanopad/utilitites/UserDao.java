package com.lazydeveloper.nanopad.utilitites;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lazydeveloper.nanopad.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("delete from User where id=:id")
    void delete(int id);

    @Query("Select * from User")
    List<User> getAllUsers();
}
