package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R

class MateriActivity : AppCompatActivity() {

    lateinit var rlLevel1 : RelativeLayout
    lateinit var rlLevel2 : RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)

        rlLevel1 = findViewById(R.id.rlLevel1)
        rlLevel2 = findViewById(R.id.rlLevel2)

        rlLevel1.setOnClickListener {
            val i = Intent(this,DetailMateriActivity::class.java)
            i.putExtra("file_name","level1.pdf")
            startActivity(i)
        }
        rlLevel2.setOnClickListener {
            val i = Intent(this,DetailMateriActivity::class.java)
            i.putExtra("file_name","level2.pdf")
            startActivity(i)
        }
    }
}