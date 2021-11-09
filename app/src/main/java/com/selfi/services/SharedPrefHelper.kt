package com.selfi.services

import android.content.Context
import android.content.SharedPreferences
import com.selfi.models.User

class SharedPrefHelper ( val mCtx: Context){
    private val PREF_NAME = "selfipref"
    private val NIS_KEY = "nis"
    private val NAMA_KEY = "nama"
    private val KELAS_KEY = "kelas"
    private val JURUSAN_KEY = "jurusan"
    private val NOHP_KEY = "nohp"
    private val PASSWORD_KEY = "password"
    private val USER_TOKEN = "user_token"


    val sharedPref: SharedPreferences =
        mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)


    fun saveUser(user: User){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(NIS_KEY,user.nis)
        editor.putString(NAMA_KEY, user.nama)
        editor.putInt(KELAS_KEY, user.Idkelas)
        editor.putString(JURUSAN_KEY, user.jurusan)
        editor.putString(NOHP_KEY, user.nohp)
        editor.putString(PASSWORD_KEY, user.password)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getInt(NIS_KEY,0) !=0
    }

    fun getAccount(): User{
        return User(
        sharedPref.getInt(NIS_KEY,0),
        sharedPref.getString(NAMA_KEY,null),
        sharedPref.getInt(KELAS_KEY,0),
        sharedPref.getString(JURUSAN_KEY,null),
        sharedPref.getString(NOHP_KEY,null),
        sharedPref.getString(PASSWORD_KEY,null))
    }


    fun clearUser(){
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    fun saveAuthToken(token: String?){
        val editor = sharedPref.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPref.getString(USER_TOKEN, null)
    }

    companion object {
        private var mInstance: SharedPrefHelper? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefHelper{
            if (mInstance == null) {
                mInstance = SharedPrefHelper(mCtx)
            }
            return mInstance as SharedPrefHelper
        }
    }
}