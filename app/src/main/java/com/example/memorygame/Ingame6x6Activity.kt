package com.example.memorygame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Chronometer
import com.example.memorygame.R.drawable.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.ingame6x6.*

class Ingame6x6Activity : AppCompatActivity() {

    var buttons: Array<ImageButton> = arrayOf();

    var images:MutableList<Int> = mutableListOf(
        img1,img2,img3, img4, img5, img6, img7, img8,img9,img10,img11, img12, img13, img14, img15, img16, img17, img18,img1,img2,img3, img4, img5, img6, img7, img8,img9,img10,img11, img12, img13, img14, img15, img16, img17, img18
    )

    fun backToMainMenu(view: View){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingame6x6)
        countTime2.start()



        buttons = arrayOf(
            btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20,btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29,btn30,btn31,btn32,btn33,btn34,btn35,btn36
        )

        val tvClickCounter : TextView = findViewById(R.id.clickAmount1)
        val tvMatchCounter : TextView = findViewById(R.id.matchAmount1)
        val tvScore : TextView = findViewById(R.id.score1)

        val countTime = findViewById<Chronometer>(R.id.countTime2)

        images.shuffle()

        val cardBack = question
        var clicked = 0
        var turnOver = false
        var lastClicked = -1

        var clickCounter = 0
        var matchCounter = 0
        var score = 0

        for (i in 0..35) {
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

                if(matchCounter == 18){
                    countTime2.stop()
                    val db = DBHelper(this, null)
                    val storeTime = countTime.text.toString()
                    val storeScore = score.toString()

                    db.addScore(storeTime, storeScore, "Hard")

                    Toast.makeText(this, "Game Finished", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}