package com.tapisdev.mediapembelajaranyuni

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.activity.*

class MainActivity : AppCompatActivity() {

    lateinit var lineLevel : LinearLayout
    lateinit var lineProfil : LinearLayout
    lateinit var lineMateri : LinearLayout
    lateinit var lineExit : LinearLayout
    lateinit var lineProfileUser : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineLevel = findViewById(R.id.lineLevel)
        lineProfil = findViewById(R.id.lineProfil)
        lineMateri = findViewById(R.id.lineMateri)
        lineExit = findViewById(R.id.lineExit)
        lineProfileUser = findViewById(R.id.lineProfileUser)


        lineLevel.setOnClickListener {
            val i = Intent(this,LevelActivity::class.java)
            startActivity(i)
        }
        lineProfil.setOnClickListener {
            val i = Intent(this,ProfilActivity::class.java)
            startActivity(i)
        }
        lineMateri.setOnClickListener {
            val i = Intent(this,DetailMateriActivity::class.java)
            i.putExtra("file_name","learning_material.pdf")
            startActivity(i)
        }
        lineExit.setOnClickListener {
            finishAffinity()
        }
        lineProfileUser.setOnClickListener {
            val i = Intent(this,UserActivity::class.java)
            startActivity(i)
        }


    }
}