package com.tapisdev.mediapembelajaranyuni.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Level2(
    var id : Int = 0,
    var soal : String = "",
    var jawaban_a : String = "",
    var jawaban_b : String = "",
    var jawaban_c : String = "",
    var jawaban_d : String = "",
    var jawaban_benar : String = "",
    var status : String = "",
    var nomor_soal : String = "",
    var teks_soal : String = "",
    var teks_bacaan : String = ""
) : Parcelable