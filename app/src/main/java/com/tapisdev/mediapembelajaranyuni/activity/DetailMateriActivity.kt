package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R

class DetailMateriActivity : BaseActivity() {

    lateinit var pdfView : PDFView
    lateinit var i : Intent
    lateinit var file_name : String
    var mPlayer : MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_materi)
        i = intent
        file_name = i.getStringExtra("file_name").toString()

        pdfView = findViewById(R.id.pdfView)

        pdfView.fromAsset(file_name)
            .enableSwipe(true)
            .load()
        setupAudio()
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }

        var namaAudio = "learning_material.mp3"

        var assetMng : AssetManager = resources.assets
        var afd2 = assetMng.openFd(namaAudio)

        //  var afd = assets.openFd(listSoal.get(currentSoal).dialog)
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mPlayer.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength())
        mPlayer.prepare()
        mPlayer.start()

        mPlayer.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            mPlayer.stop()
            mPlayer.reset()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mPlayer.stop()
        mPlayer.reset()
    }
}