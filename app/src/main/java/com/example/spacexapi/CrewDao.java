package com.example.spacexapi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CrewDao  {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert ( CrewModal crewModal );

    @Query("SELECT * FROM SpaceX")
    List<CrewModal> selectAll ();

    @Query("DELETE FROM SpaceX")
    void deleteAll();

}
