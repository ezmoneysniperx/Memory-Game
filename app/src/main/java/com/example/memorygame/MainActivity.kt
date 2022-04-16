package com.example.memorygame

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ingame4x4.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.easyBtn)
        button.setOnClickListener{
            val intent = Intent(this, Ingame4x4Activity::class.java)
            startActivity(intent)
        }
        val db = DBHelper(this, null)
        showData()

    }

    fun goToEasy(view: View) {
        val intent = Intent(this,Ingame4x4Activity::class.java)
        startActivity(intent)
    }

    fun goToHard(view: View) {
        val intent = Intent(this,Ingame6x6Activity::class.java)
        startActivity(intent)
    }

    fun showData(){
        val db = DBHelper(this, null)
        val cursor = db.getInfo()
        if(cursor!!.moveToFirst()){
            do{
                Time.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TIME_COL)) + "\n")
                Score.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.SCORE_COL)) + "\n")
                Difficulty.append(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.DIFF_COL)) + "\n")
            } while(cursor.moveToNext());
        }

        cursor.close()
    }

}