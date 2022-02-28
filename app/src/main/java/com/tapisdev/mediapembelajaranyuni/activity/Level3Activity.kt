package com.tapisdev.mediapembelajaranyuni.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.mediapembelajaranyuni.model.Level1
import com.tapisdev.mediapembelajaranyuni.model.Level3
import com.tapisdev.mediapembelajaranyuni.utility.DBAdapter
import com.tapisdev.myapplication.model.SharedVariable
import java.util.ArrayList

class Level3Activity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<Level3> = ArrayList<Level3>()
    var listSoalTemp : ArrayList<Level3> = ArrayList<Level3>()

    var TAG_LEVEL3 = "seksi_level3"
    var TAG_CHECK_ANSWER = "cekAnswer"
    var TAG_CHECK_AUDIO = "cekAudio"
    var textInfoAudio = ""
    var getAnswer = ""
    var currentSoal = 0
    var countNextSoal = 0
    var duration : Long = 600000
    var totalSkor  = 0

    var listener: DialogInterface.OnClickListener? = null
    var mPlayer : MediaPlayer = MediaPlayer()
    lateinit var mAnimation : ObjectAnimator

    lateinit var tvInfoSoal : TextView
    lateinit var imgPlay : ImageView
    lateinit var btnTrue : Button
    lateinit var btnFalse : Button
    lateinit var progress_bar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level3)

        setView()

        mDB = DBAdapter.getInstance(this)
        listSoal = mDB!!.getSoalLevel3()
        listSoalTemp = mDB!!.getSoalLevel3()

        btnTrue.setOnClickListener {
            getAnswer = "T"
            nextSoal()
        }
        btnFalse.setOnClickListener {
            getAnswer = "F"
            nextSoal()
        }
        imgPlay.setOnClickListener {
            showDialogAudio()
        }

        disableAllButton()
        startQuiz()
    }

    fun startQuiz(){
        setupProgressBarAnimation()
        showDialogAudio()
    }

    fun setupProgressBarAnimation(){
        mAnimation = ObjectAnimator.ofInt(progress_bar, "progress", 0, 100)
        mAnimation.setDuration(duration)
        mAnimation.interpolator = DecelerateInterpolator()
        mAnimation.start()
    }

    fun showDialogAudio(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_playing_audio)

        var btnEndAudio = dialog.findViewById(R.id.btnEndAudio) as Button
        var tvInfoAudio = dialog.findViewById(R.id.tvInfoAudio) as TextView
        var animation_view_audio = dialog.findViewById(R.id.animation_view_audio) as LottieAnimationView


        setupAudio()

        animation_view_audio.visibility = View.VISIBLE
        animation_view_audio.setAnimation(R.raw.listening)
        animation_view_audio.playAnimation()
        animation_view_audio.loop(true)

        Log.d(TAG_LEVEL3," "+listSoal.get(currentSoal))

        tvInfoAudio.setText(textInfoAudio)
        btnEndAudio.setOnClickListener {
            mPlayer.stop()
            mPlayer.reset()
            dialog.dismiss()

            if (listSoal.get(currentSoal).status.equals("info")){
                currentSoal++
                countNextSoal++
                setupSoal()
            }

        }

        dialog.show()
    }

    fun setupSoal(){

        enableAllButton()

        /*btnJwbA.setText("A. "+listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText("B. "+listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText("C. "+listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText("D. "+listSoal.get(currentSoal).jawaban_d)*/
        Log.d(TAG_LEVEL3,"curr soal : "+currentSoal)

        if (!listSoal.get(currentSoal).status.equals("info")){
            tvInfoSoal.setText("Soal ke - "+listSoal.get(currentSoal).nomor_soal)
            btnTrue.setText(" True ")
            btnFalse.setText(" False ")
        }else{
            disableAllButton()
            btnTrue.setText(" - ")
            btnFalse.setText(" - ")
        }

    }

    fun nextSoal(){
        Log.d(TAG_CHECK_ANSWER," Soal : "+listSoal.get(currentSoal).soal)
        Log.d(TAG_CHECK_ANSWER," getAnswer : "+getAnswer)
        Log.d(TAG_CHECK_ANSWER," Jawaban benar : "+listSoal.get(currentSoal).jawaban_benar)

        if (getAnswer.equals(listSoal.get(currentSoal).jawaban_benar)){
            totalSkor  = totalSkor + 1
        }
        countNextSoal++
        currentSoal++

        checkNextOrFinish()
    }

    fun checkNextOrFinish(){
        //jika sudah mencapai 12 record soal
        if (countNextSoal < 12){
            /*var activeSoal = currentSoal
            if (activeSoal % 5 == 0){
                showDialogAudio()
            }else{
                setupSoal()
            }*/
            setupSoal()
            showDialogAudio()

        }else{
            mAnimation.end()
            disableAllButton()

            var skorUser = ""+totalSkor
            SharedVariable.activeSkorLevel3 = SharedVariable.nilaiJawabBenarLevel3 * totalSkor
            //showSuccessMessage("Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            Log.d(TAG_LEVEL3,"Tes level 1 Selesai !, hasil skor : "+SharedVariable.activeSkorLevel3)
            val i = Intent(this,ResultActivity::class.java)
            i.putExtra("fromLevel","level3")
            startActivity(i)
        }
    }

    fun setupAudio(){
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop()
                mPlayer.reset()
            }
        }
        var activeSoal = currentSoal + 1

        var namaAudio = listSoal.get(currentSoal).soal.trim()
        Log.d(TAG_CHECK_AUDIO,"audio triimed  : "+namaAudio.length)
        Log.d(TAG_CHECK_AUDIO,"audio nya : "+listSoal.get(currentSoal).soal.length)

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

    fun setView(){
        tvInfoSoal = findViewById(R.id.tvInfoSoal)
        imgPlay = findViewById(R.id.imgPlay)
        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        progress_bar = findViewById(R.id.progress_bar)
    }

    fun disableAllButton(){
        btnTrue.isEnabled = false
        btnFalse.isEnabled = false
    }

    fun enableAllButton(){
        btnTrue.isEnabled = true
        btnFalse.isEnabled = true
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin keluar dari Tes yang sedang berlangsung ?")
        builder.setCancelable(false)

        listener = DialogInterface.OnClickListener { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                mAnimation.end()
                if (mPlayer.isPlaying()) {
                    mPlayer.stop()
                    mPlayer.reset()
                }
                SharedVariable.resetScore()
                startActivity(Intent(this, MainActivity::class.java))
            }
            if (which == DialogInterface.BUTTON_NEGATIVE) {
                dialog.cancel()
            }
        }
        builder.setPositiveButton("Yes", listener)
        builder.setNegativeButton("No", listener)
        builder.show()
    }
}

