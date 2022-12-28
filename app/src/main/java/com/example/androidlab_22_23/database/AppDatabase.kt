package com.example.androidlab_22_23.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidlab_22_23.database.dao.NoteDao
import com.example.androidlab_22_23.model.Note

@Database(entities = [Note::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao
}