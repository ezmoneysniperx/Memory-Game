package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import android.view.View
import android.widget.*
import com.example.memorygame.R.drawable.*
import kotlinx.android.synthetic.main.ingame4x4.*

class Ingame4x4Activity : AppCompatActivity() {

    var buttons: Array<ImageButton> = arrayOf();

    var images:MutableList<Int> = mutableListOf(
        img1,img2,img3, img4, img5, img6, img7, img8,img1,img2,img3, img4, img5, img6, img7, img8
    )

    fun backToMainMenu(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame4x4)
        countTime.start()

        buttons = arrayOf(
            imgbtn1,imgbtn2,imgbtn3,imgbtn4,imgbtn5,imgbtn6,imgbtn7,imgbtn8,imgbtn9,imgbtn10,imgbtn11,imgbtn12,imgbtn13,imgbtn14,imgbtn15,imgbtn16
        )

        val tvClickCounter : TextView = findViewById(R.id.clickAmount)
        val tvMatchCounter : TextView = findViewById(R.id.matchAmount)
        val tvScore : TextView = findViewById(R.id.score)

        val countTime = findViewById<Chronometer>(R.id.countTime)

        images.shuffle()

        val cardBack = question
        var clicked = 0
        var turnOver = false
        var lastClicked = -1

        var clickCounter = 0
        var matchCounter = 0
        var score = 0


        for (i in 0..15) {
            buttons[i].tag = "0"
            buttons[i].setOnClickListener {
                clickCounter++
                tvClickCounter.text = clickCounter.toString()
                if (buttons[i].tag == "0" && !turnOver) {
                    buttons[i].setImageResource(images[i])
                    buttons[i].setTag(images[i])
                    if (clicked == 0) {
                        lastClicked = i
                    }
                    clicked++
                } else if (buttons[i].tag != "0") {
                    buttons[i].setImageResource(cardBack)
                    buttons[i].tag = "0"
                    clicked--
                }


                if (clicked == 2) {
                    turnOver = true
                    if (buttons[i].tag == buttons[lastClicked].tag) {
                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turnOver = false
                        matchCounter++
                        score += 10
                        tvMatchCounter.text = matchCounter.toString()
                        tvScore.text = score.toString()
                        clicked = 0
                    }else{
                        buttons[i].setImageResource(cardBack)
                        buttons[lastClicked].setImageResource(cardBack)
                        buttons[i].tag = "0"
                        buttons[lastClicked].tag = "0"
                        turnOver = false
                        score -= 2
                        tvScore.text = score.toString()
                        clicked = 0
                    }
                } else if (clicked == 0) {
                    turnOver = false
                }

                if(matchCounter == 8) {
                    countTime.stop()
                    val db = DBHelper(this, null)
                    val storeTime = countTime.text.toString()
                    val storeScore = score.toString()

                    db.addScore(storeTime, storeScore, "Easy")

                    Toast.makeText(this, "Game Finished", Toast.LENGTH_LONG).show()

                }
            }
        }
    }



    /*fun click (view: View){
        var hangibutton:ImageButton = view as ImageButton
        var tag=hangibutton.tag.toString().toInt()
        hangibutton.setImageResource(gorseller[tag])
        hangibutton.isClickable=false
    }*/

}

