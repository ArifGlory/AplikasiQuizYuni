package com.tapisdev.mediapembelajaranyuni.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



@Parcelize
data class Level3(
    var id : Int = 0,
    var soal : String = "",
    var jawaban_benar : String = "",
    var status : String = "",
    var nomor_soal : String = "",
    var teks_soal : String = ""
) : Parcelable