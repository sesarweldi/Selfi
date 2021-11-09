package com.selfi.ui.activity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.selfi.R
import com.selfi.models.response.ResponseLogin
import com.selfi.services.SharedPrefHelper
import com.selfi.services.api.ServiceBuilder
import com.selfi.services.api.UserService
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN )

        //animation
        val anim1 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim1)
        val anim2 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim2)
        val anim3 = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.anim3)

        logoSplash.setAnimation(anim1)
        tv1_splash.setAnimation(anim2)
        tv2_splash.setAnimation(anim3)




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
        }, 5000)


    }


    private fun saveUser(){
        val pref = SharedPrefHelper(this@SplashScreen)
        val service = ServiceBuilder.buildService(UserService:: class.java, this@SplashScreen).getUserById(pref.getAccount().nis)

        service.enqueue(object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(
                    this@SplashScreen, "Error: ${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("onFailure", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
           //     Log.d("tes token", "token : ${response.body()!!.token!!}" )
               pref.saveUser(response.body()!!.user!!)
               pref.saveAuthToken(response.body()!!.token!!)
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
