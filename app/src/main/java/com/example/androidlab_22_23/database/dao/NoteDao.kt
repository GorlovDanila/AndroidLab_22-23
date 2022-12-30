package com.example.androidlab_22_23.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.androidlab_22_23.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = REPLACE)
    fun save(note: Note)

    @Insert(onConflict = REPLACE)
    fun save(note: List<Note>)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notes")
    fun deleteAll()

    @Query("SELECT * FROM notes")
    fun getAll(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Note

    @Update(onConflict = REPLACE)
    fun update(note: Note)
}