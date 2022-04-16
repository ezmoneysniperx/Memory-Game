package com.example.memorygame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + TIME_COL + " TEXT," +
                SCORE_COL + " TEXT," + DIFF_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addScore(Time : String, Score : String, Difficulty : String ){
        val values = ContentValues()
        values.put(TIME_COL, Time)
        values.put(SCORE_COL, Score)
        values.put(DIFF_COL, Difficulty)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun checkCount(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT count(*) FROM " + TABLE_NAME, null)
    }

    companion object{
        private val DATABASE_NAME = "SCORE_DATABASE"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "score_table"
        val TIME_COL = "Time"
        val SCORE_COL = "Score"
        val DIFF_COL = "Difficulty"
    }
}