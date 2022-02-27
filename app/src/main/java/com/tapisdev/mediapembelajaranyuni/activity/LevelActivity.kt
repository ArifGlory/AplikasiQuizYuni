package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R

class LevelActivity : AppCompatActivity() {

    lateinit var rlLevelElementary : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        rlLevelElementary = findViewById(R.id.rlLevelElementary)

        rlLevelElementary.setOnClickListener {
            val i = Intent(this,Level1Activity::class.java)
            startActivity(i)
        }
    }
}