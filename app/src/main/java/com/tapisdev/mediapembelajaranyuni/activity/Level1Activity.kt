package com.tapisdev.mediapembelajaranyuni.activity

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
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
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.MainActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.mediapembelajaranyuni.model.Level1
import com.tapisdev.mediapembelajaranyuni.utility.DBAdapter
import com.tapisdev.myapplication.model.SharedVariable
import kotlinx.serialization.json.Json.Default.context
import java.util.ArrayList

class Level1Activity : BaseActivity() {

    var mDB: DBAdapter? = null
    var listSoal : ArrayList<Level1> = ArrayList<Level1>()
    var listSoalTemp : ArrayList<Level1> = ArrayList<Level1>()

    var TAG_LEVEL1 = "seksi_level1"
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

    lateinit var btnJwbA : CardView
    lateinit var btnJwbB : CardView
    lateinit var btnJwbC : CardView
    lateinit var btnJwbD : CardView
    lateinit var progress_bar : ProgressBar

    lateinit var ivButtonA : ImageView
    lateinit var ivButtonB : ImageView
    lateinit var ivButtonC : ImageView
    lateinit var ivButtonD : ImageView

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level1)

        setView()

        mDB = DBAdapter.getInstance(this)
        listSoal = mDB!!.getSoalLevel1()
        listSoalTemp = mDB!!.getSoalLevel1()

        btnJwbA.setOnClickListener {
            getAnswer = "A"
            nextSoal()
        }
        btnJwbB.setOnClickListener {
            getAnswer = "B"
            nextSoal()
        }
        btnJwbC.setOnClickListener {
            getAnswer = "C"
            nextSoal()
        }
        btnJwbD.setOnClickListener {
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

        tvTeksSoal.setText(Html.fromHtml(listSoal.get(currentSoal).teks_soal,Html.FROM_HTML_MODE_COMPACT))
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
        tvInfoSoal.setText("Soal ke - "+currentSoal)

        /*btnJwbA.setText("A. "+listSoal.get(currentSoal).jawaban_a)
        btnJwbB.setText("B. "+listSoal.get(currentSoal).jawaban_b)
        btnJwbC.setText("C. "+listSoal.get(currentSoal).jawaban_c)
        btnJwbD.setText("D. "+listSoal.get(currentSoal).jawaban_d)*/
        Log.d(TAG_LEVEL1,"curr soal : "+currentSoal)
        Log.d(TAG_LEVEL1,"gambar a : "+listSoal.get(currentSoal).jawaban_a)
        Log.d(TAG_LEVEL1,"res gambar a : "+getResourceGambar(listSoal.get(currentSoal).jawaban_a))

        if (!listSoal.get(currentSoal).status.equals("info")){
            ivButtonA.setImageResource(getResourceGambar(listSoal.get(currentSoal).jawaban_a))
            ivButtonB.setImageResource(getResourceGambar(listSoal.get(currentSoal).jawaban_b))
            ivButtonC.setImageResource(getResourceGambar(listSoal.get(currentSoal).jawaban_c))
            ivButtonD.setImageResource(getResourceGambar(listSoal.get(currentSoal).jawaban_d))
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
        //jika sudah mencapai 6 record soal
        if (countNextSoal < 6){
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
            SharedVariable.activeSkorLevel1 = SharedVariable.nilaiJawabBenarLevel1 * totalSkor
            //showSuccessMessage("Tes Mendengarkan Selesai !, jawaban benar : "+skorUser)
            Log.d(TAG_LEVEL1,"Tes level 1 Selesai !, hasil skor : "+SharedVariable.activeSkorLevel1)
            val i = Intent(this,ResultActivity::class.java)
            i.putExtra("fromLevel","level1")
            startActivity(i)
        }
    }

    fun getResourceGambar(nama_gambar : String): Int {
        var namanya_aja = nama_gambar.substringBefore(".")

        val resources: Resources = getResources()
        var resourceId = resources.getIdentifier(namanya_aja,"drawable",packageName)

        return  resourceId
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
        btnJwbA = findViewById(R.id.btnJwbA)
        btnJwbB = findViewById(R.id.btnJwbB)
        btnJwbC = findViewById(R.id.btnJwbC)
        btnJwbD = findViewById(R.id.btnJwbD)
        ivButtonA = findViewById(R.id.ivButtonA)
        ivButtonB = findViewById(R.id.ivButtonB)
        ivButtonC = findViewById(R.id.ivButtonC)
        ivButtonD = findViewById(R.id.ivButtonD)
        progress_bar = findViewById(R.id.progress_bar)
    }

    fun disableAllButton(){
        btnJwbA.isEnabled = false
        btnJwbB.isEnabled = false
        btnJwbC.isEnabled = false
        btnJwbD.isEnabled = false
    }

    fun enableAllButton(){
        btnJwbA.isEnabled = true
        btnJwbB.isEnabled = true
        btnJwbC.isEnabled = true
        btnJwbD.isEnabled = true
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