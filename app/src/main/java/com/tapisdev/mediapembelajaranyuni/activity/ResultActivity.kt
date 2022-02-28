package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.myapplication.model.SharedVariable

class ResultActivity : BaseActivity() {

    lateinit var  btnKeHome : Button
    lateinit var tvSkor : TextView
    lateinit var i : Intent
    var fromLevel  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        i = intent
        fromLevel = i.getStringExtra("fromLevel").toString()

        btnKeHome = findViewById(R.id.btnKeHome)
        tvSkor = findViewById(R.id.tvSkor)

        btnKeHome.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        if (fromLevel.equals("level1")){
            tvSkor.setText(""+SharedVariable.activeSkorLevel1.toInt())
        }else if (fromLevel.equals("level2")){
            tvSkor.setText(""+SharedVariable.activeSkorLevel2.toInt())
        }else if (fromLevel.equals("level3")){
            tvSkor.setText(""+SharedVariable.activeSkorLevel3.toInt())
        }else if (fromLevel.equals("level4")){
            tvSkor.setText(""+SharedVariable.activeSkorLevel4.toInt())
        }


    }
}