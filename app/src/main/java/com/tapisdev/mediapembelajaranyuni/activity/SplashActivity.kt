package com.tapisdev.mediapembelajaranyuni.activity

import android.content.Intent
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R

class SplashActivity : BaseActivity() {

    lateinit var btnStart : Button
    var mPlayer : MediaPlayer = MediaPlayer()
    var TAG_dETAIL = "fbSplash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            mPlayer.stop()
            mPlayer.reset()

            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
        setupAudio()

        /*val i = Intent(this, MainActivity::class.java)

        object : CountDownTimer(2500, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                startActivity(i)
            }
        }.start()*/
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }

        var namaAudio = "audio_splash_screen.mp3"

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
}