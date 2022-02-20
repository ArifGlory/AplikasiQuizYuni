package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R

class DetailMateriActivity : BaseActivity() {

    lateinit var pdfView : PDFView
    lateinit var i : Intent
    lateinit var file_name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)
        i = intent
        file_name = i.getStringExtra("file_name").toString()

        pdfView = findViewById(R.id.pdfView)

        pdfView.fromAsset(file_name)
            .enableSwipe(true)
            .load()
    }
}