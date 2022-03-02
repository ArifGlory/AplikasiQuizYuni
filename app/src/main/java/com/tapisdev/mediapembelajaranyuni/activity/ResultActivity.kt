package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.auth.User
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.mediapembelajaranyuni.model.UserPreference
import com.tapisdev.myapplication.model.SharedVariable

class ResultActivity : BaseActivity() {

    lateinit var  btnKeHome : Button
    lateinit var tvSkor : TextView
    lateinit var txtJudul : TextView
    lateinit var i : Intent
    var fromLevel  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        mUserPref = UserPreference(this)
        i = intent
        fromLevel = i.getStringExtra("fromLevel").toString()

        btnKeHome = findViewById(R.id.btnKeHome)
        tvSkor = findViewById(R.id.tvSkor)
        txtJudul = findViewById(R.id.txtJudul)

        btnKeHome.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        txtJudul.setText(mUserPref.getName() + " Your Score is")

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