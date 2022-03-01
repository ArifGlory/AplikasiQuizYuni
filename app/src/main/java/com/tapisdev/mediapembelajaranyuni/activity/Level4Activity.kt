package com.tapisdev.mediapembelajaranyuni.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.airbnb.lottie.LottieAnimationView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.mediapembelajaranyuni.model.Level3
import com.tapisdev.mediapembelajaranyuni.model.Level4
import com.tapisdev.mediapembelajaranyuni.utility.DBAdapter
import com.tapisdev.myapplication.model.SharedVariable
import java.util.ArrayList

class Level4Activity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<Level4> = ArrayList<Level4>()
    var listSoalTemp : ArrayList<Level4> = ArrayList<Level4>()

    var TAG_LEVEL4 = "seksi_level4"
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
    lateinit var btnJawabA : Button
    lateinit var btnJawabB : Button
    lateinit var btnJawabC : Button
    lateinit var btnJawabD : Button
    lateinit var progress_bar : ProgressBar


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level4)

        setView()

        mDB = DBAdapter.getInstance(this)
        listSoal = mDB!!.getSoalLevel4()
        listSoalTemp = mDB!!.getSoalLevel4()

        btnJawabA.setOnClickListener {
            getAnswer = "A"
            nextSoal()
        }
        btnJawabB.setOnClickListener {
            getAnswer = "B"
            nextSoal()
        }
        btnJawabC.setOnClickListener {
            getAnswer = "C"
            nextSoal()
        }
        btnJawabD.setOnClickListener {
            getAnswer = "D"
            nextSoal()
        }
        imgPlay.setOnClickListener {
            showDialogAudio()
        }

        disableAllButton()
        startQuiz()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun startQuiz(){
        setupProgressBarAnimation()
        showDialogTeksSoal()
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

        Log.d(TAG_LEVEL4," "+listSoal.get(currentSoal))

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

    @RequiresApi(Build.VERSION_CODES.N)
    fun showDialogTeksSoal(){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_teks_soal)

        var btnEndAudio = dialog.findViewById(R.id.btnEndAudio) as Button
        var tvTeksSoal = dialog.findViewById(R.id.tvTeksSoal) as TextView

        setupAudio()

        tvTeksSoal.setText(Html.fromHtml(listSoal.get(currentSoal).teks_soal, Html.FROM_HTML_MODE_COMPACT))
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

    @RequiresApi(Build.VERSION_CODES.N)
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

    @RequiresApi(Build.VERSION_CODES.N)
    fun checkNextOrFinish(){
        //jika sudah mencapai 12 record soal
        if (countNextSoal < 14){
            /*var activeSoal = currentSoal
            if (activeSoal % 5 == 0){
                showDialogAudio()
            }else{
                setupSoal()
            }*/
            setupSoal()
            if (listSoal.get(currentSoal).status.equals("info")){
                showDialogTeksSoal()
            }else{
                showDialogAudio()
            }


        }else{
            mAnimation.end()
            disableAllButton()

            var skorUser = ""+totalSkor
            SharedVariable.activeSkorLevel4 = SharedVariable.nilaiJawabBenarLevel4 * totalSkor
            //showSuccessMessage("Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            Log.d(TAG_LEVEL4,"Tes level 1 Selesai !, hasil skor : "+SharedVariable.activeSkorLevel4)
            val i = Intent(this,ResultActivity::class.java)
            i.putExtra("fromLevel","level4")
            startActivity(i)
        }
    }

    fun setupSoal(){

        enableAllButton()

        /*btnJwbA.setText("A. "+listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText("B. "+listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText("C. "+listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText("D. "+listSoal.get(currentSoal).jawaban_d)*/
        Log.d(TAG_LEVEL4,"curr soal : "+currentSoal)

        if (!listSoal.get(currentSoal).status.equals("info")){
            tvInfoSoal.setText("Question Number - "+listSoal.get(currentSoal).nomor_soal)
            btnJawabA.setText(listSoal.get(currentSoal).jawaban_a)
            btnJawabB.setText(listSoal.get(currentSoal).jawaban_b)
            btnJawabC.setText(listSoal.get(currentSoal).jawaban_c)
            btnJawabD.setText(listSoal.get(currentSoal).jawaban_d)
        }else{
            disableAllButton()
            btnJawabA.setText("-")
            btnJawabB.setText("-")
            btnJawabC.setText("-")
            btnJawabD.setText("-")
        }

    }

    fun setupProgressBarAnimation(){
        mAnimation = ObjectAnimator.ofInt(progress_bar, "progress", 0, 100)
        mAnimation.setDuration(duration)
        mAnimation.interpolator = DecelerateInterpolator()
        mAnimation.start()
    }

    fun setView(){
        tvInfoSoal = findViewById(R.id.tvInfoSoal)
        imgPlay = findViewById(R.id.imgPlay)
        btnJawabA = findViewById(R.id.btnJawabA)
        btnJawabB = findViewById(R.id.btnJawabB)
        btnJawabC = findViewById(R.id.btnJawabC)
        btnJawabD = findViewById(R.id.btnJawabD)
        progress_bar = findViewById(R.id.progress_bar)
    }

    fun disableAllButton(){
        btnJawabA.isEnabled = false
        btnJawabB.isEnabled = false
        btnJawabC.isEnabled = false
        btnJawabD.isEnabled = false
        imgPlay.isEnabled = false
    }

    fun enableAllButton(){
        btnJawabA.isEnabled = true
        btnJawabB.isEnabled = true
        btnJawabC.isEnabled = true
        btnJawabD.isEnabled = true
        imgPlay.isEnabled = true
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this)
        builder.setMessage("Do you want to exit the ongoing Test ?")
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