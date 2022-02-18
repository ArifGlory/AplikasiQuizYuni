package com.tapisdev.mediapembelajaranyuni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.activity.LevelActivity

class MainActivity : BaseActivity() {

    lateinit var lineLevel : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineLevel = findViewById(R.id.lineLevel)


        lineLevel.setOnClickListener {
            val i = Intent(this,LevelActivity::class.java)
            startActivity(i)
        }


    }
}