package com.masters.recyclerview.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.masters.recyclerview.database.model.modelDatabase;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert(modelDatabase modelDatabase);

    @Query("SELECT * FROM user_Info")
    List<modelDatabase> getAllUsers();

}
