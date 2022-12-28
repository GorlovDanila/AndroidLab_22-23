package com.example.androidlab_22_23.model

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidlab_22_23.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(context: Context) {

    private val db by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    private val noteDao by lazy {
        db.getNoteDao()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE notes")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE 'notes' ('id' INTEGER, 'title' TEXT NOT NULL, 'description' TEXT NOT NULL, 'date' INTEGER, 'longitude' REAL, 'latitude' REAL, PRIMARY KEY ('id'))")
        }
    }

    suspend fun saveNote(note: Note) = withContext(Dispatchers.IO) {
        noteDao.save(note)
    }

    suspend fun delete(note: Note) = withContext(Dispatchers.IO) {
        noteDao.delete(note)
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        noteDao.deleteAll()
    }

    suspend fun getNoteById(id: Int): Note = withContext(Dispatchers.IO) {
        noteDao.getNoteById(id)
    }

    suspend fun getAll(): List<Note> = withContext(Dispatchers.IO) {
        noteDao.getAll()
    }

    suspend fun updateNote(note: Note) = withContext(Dispatchers.IO) {
        noteDao.update(note)
    }

    companion object {
        private const val DATABASE_NAME = "note_db"
    }
}