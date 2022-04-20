package com.tapisdev.mediapembelajaranyuni

import android.content.Intent
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.activity.*

class MainActivity : AppCompatActivity() {

    lateinit var lineLevel : LinearLayout
    lateinit var lineProfil : LinearLayout
    lateinit var lineMateri : LinearLayout
    lateinit var lineExit : LinearLayout
    lateinit var lineProfileUser : LinearLayout

    var mPlayer : MediaPlayer = MediaPlayer()

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
            i.putExtra("file_name","materi_fix.pdf")
            startActivity(i)
        }
        lineExit.setOnClickListener {
            finishAffinity()
        }
        lineProfileUser.setOnClickListener {
            val i = Intent(this,UserActivity::class.java)
            startActivity(i)
        }

        setupAudio()
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }


        val namaAudio = "backsound_home.mp3"
        val assetMng : AssetManager = resources.assets
        val afd2 = assetMng.openFd(namaAudio)

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

    fun stopAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
                setupAudio()
            }
        }

    }

    override fun onPause() {
        super.onPause()
        stopAudio()
    }

    override fun onStop() {
        super.onStop()
        stopAudio()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAudio()
    }
}