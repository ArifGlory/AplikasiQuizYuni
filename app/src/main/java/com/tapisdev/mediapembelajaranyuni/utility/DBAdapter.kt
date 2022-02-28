package com.tapisdev.mediapembelajaranyuni.utility

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.tapisdev.mediapembelajaranyuni.model.Level1
import com.tapisdev.mediapembelajaranyuni.model.Level3
import java.lang.Exception
import java.util.ArrayList

/**
 * Created by Glory on 28/09/2016.
 */
class DBAdapter
/**
 * private Constructor, untuk menggunakan kelas ini gunakan getInstance()
 *
 * @param context
 */
private constructor(context: Context) : SQLiteAssetHelper(context, DB_NAME, null, DB_VER) {
    var listSoalLevel1 : ArrayList<Level1> = ArrayList<Level1>()
    var listSoalLevel3 : ArrayList<Level3> = ArrayList<Level3>()


    fun ambilDB(): SQLiteDatabase? {
        db = this.writableDatabase
        return db
    }

    /* public DBAdapter getInstance(Context context){

        if (dbInstance == null){

            dbInstance = new DBAdapter(context);
            db = dbInstance.getReadableDatabase();

        }

        return  dbInstance;
    }*/
    @Synchronized
    override fun close() {
        super.close()
        if (dbInstance != null) {
            dbInstance!!.close()
        }
    }

    //method untuk mengambil semua data soal level 1
    fun getSoalLevel1(): ArrayList<Level1> {
        //var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
        var tabel_soal: String? = "tb_level_1"
        listSoalLevel1.clear()

        val cursor = db!!.query(
            tabel_soal, arrayOf(
                "id",
                "soal",
                "jawaban_a",
                "jawaban_b",
                "jawaban_c",
                "jawaban_d",
                "jawaban_benar",
                "status"
            ), null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                var quiz  : Level1 = Level1(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_a")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_b")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_c")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_d")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_benar")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status"))
                )
               listSoalLevel1.add(quiz)
            } while (cursor.moveToNext())
        }
        return listSoalLevel1
    }

    //method untuk mengambil semua data soal level 3
    fun getSoalLevel3(): ArrayList<Level3> {
        //var listSoal : ArrayList<SeksiMendengarkan> = ArrayList<SeksiMendengarkan>()
        var tabel_soal: String? = "tb_level_3"
        listSoalLevel3.clear()

        val cursor = db!!.query(
            tabel_soal, arrayOf(
                "id",
                "soal",
                "jawaban_benar",
                "status",
                "nomor_soal"
            ), null, null, null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                var quiz  : Level3 = Level3(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("soal")),
                    cursor.getString(cursor.getColumnIndexOrThrow("jawaban_benar")),
                    cursor.getString(cursor.getColumnIndexOrThrow("status")),
                    cursor.getString(cursor.getColumnIndexOrThrow("nomor_soal"))
                )
                listSoalLevel3.add(quiz)
            } while (cursor.moveToNext())
        }
        return listSoalLevel3
    }



    /*
    public List<History> getAllHistory(){
        List<History> listHistory = new ArrayList<>();

        Cursor cursor = db.query(TABLE_HISTORY,new String[]{

                COL_SOAL_ID,
                COL_HISTORI_NAMA_PAKET,
                COL_HISTORI_SKOR,
                COL_HISTORI_WAKTU,
        },null,null,null,null,null);//kenapa ada 5 null ya ?

        if (cursor.moveToFirst()){

            do {
                Quiz quiz = new Quiz();
                History history = new History();

                history.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_SOAL_ID)));
                history.setNamaPaket(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_NAMA_PAKET)));
                history.setSkor(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_SKOR)));
                history.setWaktu(cursor.getString(cursor.getColumnIndexOrThrow(COL_HISTORI_WAKTU)));

                listHistory.add(history);
            }while (cursor.moveToNext());

        }

        return listHistory;
    }*/
    fun insertHistory(namaPaket: String?, skor: String?, waktu: String?): Long {
        val db = this.writableDatabase
        val initialValues = ContentValues()
        var retval: Long = 0
        try {
            initialValues.put("nama_paket_soal", namaPaket)
            initialValues.put("skor", skor)
            initialValues.put("waktu", waktu)
            retval = db.insert(TABLE_HISTORY, null, initialValues)
        } catch (e: Exception) {
            Log.e(TAG_INSERT, "insertRow exception", e)
        } finally {
            db.close()
        }
        return retval
    }

    companion object {
        private const val DB_NAME = "db_yuni_quiz_2"
        private const val DB_VER = 1
        const val TABLE_LEVEL1 = "tb_level_1"
        const val TABLE_HISTORY = "tb_history"
        const val COL_MENDENGARKAN_ID = "id_soal"
        const val COL_MENDENGARKAN_DIALOG = "dialog"
        const val COL_MENDENGARKAN_GAMBAR = "gambar"
        const val COL_MENDENGARKAN_SOAL = "soal"
        const val COL_MENDENGARKAN_JAWABAN_A = "jawaban_a"
        const val COL_MENDENGARKAN_JAWABAN_B = "jawaban_b"
        const val COL_MENDENGARKAN_JAWABAN_C = "jawaban_c"
        const val COL_MENDENGARKAN_JAWABAN_D = "jawaban_d"
        const val COL_MENDENGARKAN_JAWABAN_BENAR = "jawaban_benar"
        const val TAG_INSERT = "insertDB"
        var dbInstance: DBAdapter? = null
        var db: SQLiteDatabase? = null
        @Synchronized
        fun getInstance(context: Context): DBAdapter? {
            if (dbInstance == null) {
                dbInstance = DBAdapter(context.applicationContext)
                db = dbInstance!!.readableDatabase
            }
            return dbInstance
        }
    }
}