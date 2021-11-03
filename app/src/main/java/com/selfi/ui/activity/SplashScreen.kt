package com.selfi.ui.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.selfi.R
import com.selfi.models.response.ResponseLogin
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val pref = SharedPrefHelper(this@SplashScreen)

        Handler().postDelayed({
            DoAsync(
                {if (pref.isLoggedIn()){saveUser()} },
                {
                    if(pref.isLoggedIn()){
                        isLogin()
                    }
                    else{
                        isNotLogin()
                    }
                }
            ).execute()
        }, 2000)


    }


    private fun saveUser(){
        val pref = SharedPrefHelper(this@SplashScreen)
        val service = ServiceBuilder.buildService(UserService:: class.java).getUserById(pref.getAccount().nis)

        service.enqueue(object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(
                    this@SplashScreen, "Error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("onFailure", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
               pref.saveUser(response.body()!!.user!!)
            }

        })
    }


    private fun isLogin(){
        startActivity(
            Intent(
                this@SplashScreen,
                MainActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }

    private fun isNotLogin(){
        startActivity(
            Intent(
                this@SplashScreen,
                LoginActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }


    private class DoAsync(val onBackground: () -> Unit, val onPost:() -> Unit):
        AsyncTask<Void, Void, Void>(){
        override fun doInBackground(vararg params: Void?): Void? {
            onBackground()
            return null
        }

        override fun onPostExecute(result: Void?) {
            onPost()
        }
    }
}
