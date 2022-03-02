package com.tapisdev.mediapembelajaranyuni.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.tapisdev.lokamotor.base.BaseActivity
import com.tapisdev.mediapembelajaranyuni.R
import com.tapisdev.mediapembelajaranyuni.model.UserPreference

class UserActivity : BaseActivity() {

    lateinit var edName : EditText
    lateinit var edClass : EditText
    lateinit var btnSave : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        mUserPref = UserPreference(this)

        edName = findViewById(R.id.edName)
        edClass = findViewById(R.id.edClass)
        btnSave = findViewById(R.id.btnSave)

        btnSave.setOnClickListener {
            checkValidation()
        }

        updateUI()
    }

    fun updateUI(){
        edName.setText(mUserPref.getName())
        edClass.setText(mUserPref.getKelas())
    }

    fun checkValidation(){
        var name = edName.text.toString()
        var kelas = edClass.text.toString()

        if (name.equals("") || name.length == 0){
            showErrorMessage("Name Required")
        }else  if (kelas.equals("") || kelas.length == 0){
            showErrorMessage("Class Required")
        }else{
            saveUser(name,kelas)
        }
    }

    fun saveUser(nama : String,kelas : String){
        mUserPref.saveName(nama)
        mUserPref.saveKelas(kelas)
        showSuccessMessage("Data Saved !")
        onBackPressed()
    }
}