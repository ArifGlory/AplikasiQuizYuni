package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R

class LevelActivity : AppCompatActivity() {

    lateinit var rlLevelElementary : RelativeLayout
    lateinit var cardlistIntermediate : CardView
    lateinit var cardlistAdvance : CardView
    lateinit var cardlistBeginner : CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        rlLevelElementary = findViewById(R.id.rlLevelElementary)
        cardlistIntermediate = findViewById(R.id.cardlistIntermediate)
        cardlistAdvance = findViewById(R.id.cardlistAdvance)
        cardlistBeginner = findViewById(R.id.cardlistBeginner)

        rlLevelElementary.setOnClickListener {
            val i = Intent(this,Level1Activity::class.java)
            startActivity(i)
        }
        cardlistIntermediate.setOnClickListener {
            val i = Intent(this,Level3Activity::class.java)
            startActivity(i)
        }
        cardlistAdvance.setOnClickListener {
            val i = Intent(this,Level4Activity::class.java)
            startActivity(i)
        }
        cardlistBeginner.setOnClickListener {
            val i = Intent(this,Level2Activity::class.java)
            startActivity(i)
        }
    }
}