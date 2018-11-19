package com.example.user.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BacklogDao {
    @Query("SELECT * FROM backlogGame")
    public List<Backlog> getAllBacklogGames();

    @Insert
    public void insertBacklogGame(Backlog backlogGame);

    @Delete
    public void deleteBacklogGame(Backlog backlogGame);

    @Update
    public void updateBacklogGame(Backlog backlogGame);
}